package daxo.the.anikat.fragments.browse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import daxo.the.anikat.R
import daxo.the.anikat.databinding.FragmentExploreBinding
import daxo.the.anikat.fragments.browse.data.entity.MediaCardData
import daxo.the.anikat.fragments.browse.data.entity.MediaLineData
import daxo.the.anikat.fragments.browse.data.viewmodel.ExploreViewModel
import daxo.the.anikat.fragments.browse.util.decorator.CenteredRVDecorator
import daxo.the.anikat.fragments.browse.util.recview.ExploreMediaRVAdapter
import daxo.the.anikat.main_activity.MainActivity
import daxo.the.anikat.type.MediaType
import jp.wasabeef.recyclerview.animators.FadeInUpAnimator
import kotlinx.coroutines.launch


@AndroidEntryPoint
abstract class ExploreFragment : Fragment() {

    abstract val mediaType: MediaType

    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!

    abstract val viewModel: ExploreViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRVAdapter()

        val adapter = binding.recyclerView.adapter as ExploreMediaRVAdapter
        viewModel.viewModelScope.launch {
            launch {
                viewModel.reloadDataFlow(mediaType)
            }
            viewModel.getData().collect { input ->
                adapter.data = input
            }
        }

        initRecyclerViewDecoration()

        binding.searchView.setOnClickListener {
            binding.searchView.isIconified = false
        }

        binding.searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (hasFocus) binding.testFloating1.hide() else binding.testFloating1.show()
        }
    }

    private fun initRVAdapter() {
        val layoutManager =
            LinearLayoutManager(this.requireContext(), LinearLayoutManager.VERTICAL, false)
        val adapter = ExploreMediaRVAdapter(this.requireContext())

        val interactListener = object : ExploreMediaRVAdapter.ExploreMediaRVAdapterListener {
            override fun mediaLineClicked(dataLineData: MediaLineData) {
                println(
                    dataLineData.data
                        .groupBy { it.mediaId }
                        .map { "${it.key} - ${it.value.size}" }
                        .joinToString(separator = "\n")
                )
            }

            override fun mediaItemClicked(
                data: MediaLineData,
                mediaCardData: MediaCardData,
                position: Int
            ) {
                (requireActivity() as MainActivity).cardClicked(mediaCardData)
            }

            override suspend fun requirePaginate(
                data: MediaLineData,
                func: suspend (MediaLineData, Boolean) -> Unit
            ) {
                viewModel.paginateLine(mediaType, data, func)
            }

        }
        adapter.interactListener = interactListener

        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
    }

    private fun initRecyclerViewDecoration() {
        val bottomMargin = resources.getDimensionPixelSize(R.dimen.exploreFragmentBaseMargin)
        val searchBarHeight =
            resources.getDimensionPixelSize(R.dimen.exploreFragmentSearchViewHeight)

        binding.recyclerView.addItemDecoration(CenteredRVDecorator(searchBarHeight, bottomMargin))
        binding.recyclerView.itemAnimator = FadeInUpAnimator().apply {
            moveDuration = 300
            addDuration = 300
            changeDuration = 300
            removeDuration = 300
        }
    }
}