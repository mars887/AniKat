package daxo.the.anikat.fragments.mediaviewinghistory

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import daxo.core.model.media.media.extended.ExtendedMediaCardViewed
import daxo.the.anikat.R
import daxo.the.anikat.databinding.FragmentExploreBinding
import daxo.the.anikat.fragments.browse.util.decorator.ExploreMediaRVDecorator
import daxo.the.anikat.main_activity.MainActivity
import jp.wasabeef.recyclerview.animators.FadeInUpAnimator
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MediaHistoryFragment : Fragment() {

    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MediaHistoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.recyclerView

        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        val adapter = MediaHistoryRVAdapter(object : MediaHistoryRVAdapter.MediaHistoryAdapterListener {
            override fun invoke(card: ExtendedMediaCardViewed) {                                  // on card click listener
                (requireActivity() as MainActivity).cardClicked(card.toExtendedMediaCard())
            }
        }, requireContext())

        recyclerView.adapter = adapter

        initRecyclerViewDecoration()

        lifecycleScope.launch {
            viewModel.data.collect {
                adapter.data = it
            }
        }

        binding.searchView.setOnClickListener {
            binding.searchView.isIconified = false
        }

        binding.searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (hasFocus) binding.testFloating1.hide() else binding.testFloating1.show()
        }
    }

    private fun initRecyclerViewDecoration() {
        val bottomMargin = resources.getDimensionPixelSize(R.dimen.exploreFragmentBaseMargin)
        val searchBarHeight = resources.getDimensionPixelSize(R.dimen.mediaHistorySearchViewHeight)

        val recyclerView = binding.recyclerView
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager

        recyclerView.addItemDecoration(ExploreMediaRVDecorator(searchBarHeight, bottomMargin))
        recyclerView.itemAnimator = FadeInUpAnimator().apply {
            moveDuration = 300
            addDuration = 300
            changeDuration = 300
            removeDuration = 300
        }

        recyclerView.addItemDecoration(
            MediaHistoryRVDecorator(
                requireContext().resources.getDimensionPixelSize(R.dimen.defaultMargin),
                requireContext().resources.getDimensionPixelSize(R.dimen.defaultMargin),
            )
        )


        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val totalItemCount = layoutManager.itemCount
                val lastItemPosition = layoutManager.findLastVisibleItemPosition()

                if (lastItemPosition >= totalItemCount - 2) {
                    viewModel.loadNextPage()
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.tryLoadNew()
        Log.i("MHF", "onResume: mhf")
    }
}