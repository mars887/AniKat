package daxo.the.anikat.fragments.browse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import daxo.core.model.media.ExtendedMediaCard
import daxo.core.model.media.enums.MediaType
import daxo.the.anikat.R
import daxo.the.anikat.databinding.FragmentExploreBinding
import daxo.the.anikat.fragments.browse.data.entity.ExtendedMediaCardListScrollable
import daxo.the.anikat.fragments.browse.data.viewmodel.ExploreViewModel
import daxo.the.anikat.fragments.browse.util.decorator.ExploreMediaRVDecorator
import daxo.the.anikat.fragments.browse.util.recview.ExploreMediaRVAdapter
import daxo.the.anikat.main_activity.MainActivity
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

        if (viewModel.mediaType == null) viewModel.mediaType = mediaType

        val adapter = initRVAdapter()

        viewModel.viewModelScope.launch {
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

    /* --- INITIALIZING RV ADAPTER --- */

    private fun initRVAdapter(): ExploreMediaRVAdapter {
        val layoutManager = LinearLayoutManager(this.requireContext(), LinearLayoutManager.VERTICAL, false)
        val adapter = ExploreMediaRVAdapter(this.requireContext())

        val interactListener = object : ExploreMediaRVAdapter.ExploreMediaRVAdapterListener {
            override fun mediaLineClicked(dataLineData: ExtendedMediaCardListScrollable) {
                mediaLineClickedAction()
            }

            override fun mediaItemClicked(data: ExtendedMediaCardListScrollable, mediaCardData: ExtendedMediaCard, position: Int) {
                mediaCardClickedAction(mediaCardData)
            }

            override fun requirePaginate(data: ExtendedMediaCardListScrollable) {
                requirePaginateAction(data)
            }

        }

        adapter.interactListener = interactListener

        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        return adapter
    }

    /* --- MEDIA ACTIONS --- */

    private fun mediaLineClickedAction() {
        //TODO("Not yet implemented")
    }

    private fun requirePaginateAction(data: ExtendedMediaCardListScrollable) {
        viewModel.paginateMediaList(data)
    }

    private fun mediaCardClickedAction(mediaCardData: ExtendedMediaCard) {
        (requireActivity() as MainActivity).cardClicked(mediaCardData)
    }

    /* --- RECYCLER VIEW DECORATION --- */

    private fun initRecyclerViewDecoration() {
        val bottomMargin = resources.getDimensionPixelSize(R.dimen.exploreFragmentBaseMargin)
        val searchBarHeight = resources.getDimensionPixelSize(R.dimen.exploreFragmentSearchViewHeight)

        binding.recyclerView.addItemDecoration(ExploreMediaRVDecorator(searchBarHeight, bottomMargin))
        binding.recyclerView.itemAnimator = FadeInUpAnimator().apply {
            moveDuration = 300
            addDuration = 300
            changeDuration = 300
            removeDuration = 300
        }
    }

    companion object {
        private const val TAG = "ExploreFragment"
    }
}