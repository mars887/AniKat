package daxo.the.anikat.fragments.browse.util.recview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import daxo.the.anikat.R
import daxo.the.anikat.databinding.ExploreRvTestItemBinding
import daxo.the.anikat.fragments.browse.data.entity.*
import daxo.the.anikat.fragments.browse.util.decorator.MediaLineRVDecorator
import daxo.the.anikat.fragments.browse.util.diffutil.ExploreMediaDiffUtilImpl
import daxo.core.model.media.media.extended.ExtendedMediaCard
import jp.wasabeef.recyclerview.animators.FadeInAnimator

class ExploreMediaRVAdapter(
    private val context: Context,
) : RecyclerView.Adapter<ExploreMediaRVAdapter.MediaLineViewHolder>() {

    var interactListener: ExploreMediaRVAdapterListener? = null

    /* --- DATA WITH DIFF UTIL --- */

    var data = listOf<ExtendedMediaCardListScrollable>()
        set(value) {
            val callback = ExploreMediaDiffUtilImpl(field, value)
            field = value
            DiffUtil.calculateDiff(callback).dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaLineViewHolder {
        val binding =
            ExploreRvTestItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MediaLineViewHolder(binding)
    }

    /* --- ON BIND --- */

    override fun onBindViewHolder(holder: MediaLineViewHolder, position: Int) {
        data[position].let {
            holder.bind(it, context, interactListener)
        }
    }

    override fun onBindViewHolder(holder: MediaLineViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) onBindViewHolder(holder, position)
        else if (payloads[0] is String) {
            val payload = payloads[0] as String

            if (payload.contains("cards")) holder.updateCards(data[position])
            if (payload.contains("lineName")) holder.updateLineName(data[position].extendedMediaCardList.listName)
        }
    }

    override fun getItemCount(): Int = data.size

    /* --- HOLDER CLASS --- */

    class MediaLineViewHolder(private val binding: ExploreRvTestItemBinding) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var adapter: MediaLineRVAdapter
        private lateinit var interactListener: ExploreMediaRVAdapterListener

        fun bind(
            data: ExtendedMediaCardListScrollable,
            context: Context,
            interactListener: ExploreMediaRVAdapterListener?,
        ) {
            if (interactListener != null) this.interactListener = interactListener

            binding.animeLineTitleView.text = data.extendedMediaCardList.listName  // setting list name

            val recyclerView = binding.innerRecyclerView

            adapter = MediaLineRVAdapter(interactListener)
            adapter.data = data

            val layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = adapter

            if (recyclerView.itemDecorationCount == 0) {
                recyclerView.addItemDecoration(
                    MediaLineRVDecorator(
                        context.resources.getDimensionPixelSize(
                            R.dimen.exploreFragmentBaseMargin
                        )
                    )
                )
            }

            initScroll(recyclerView, layoutManager, interactListener, data) // init scroll

            recyclerView.itemAnimator = FadeInAnimator().apply {    // items animation
                moveDuration = 500
                addDuration = 500
                changeDuration = 500
                removeDuration = 500
            }

            layoutManager.scrollToPosition(data.scrollPosition)
        }

        private fun initScroll(
            recyclerView: RecyclerView,
            layoutManager: LinearLayoutManager,
            interactListener: ExploreMediaRVAdapterListener?,
            data: ExtendedMediaCardListScrollable
        ) {
            recyclerView.clearOnScrollListeners()

            recyclerView.addOnScrollListener(MediaLineOnScrollListener(
                layoutManager, {
                    interactListener?.requirePaginate(data) // on paginate required
                }, {
                    data.scrollPosition = layoutManager.findFirstVisibleItemPosition() // any scroll
                })
            )

            binding.animeLineTitleView.setOnClickListener {
                interactListener?.mediaLineClicked(data)
            }
        }

        fun updateCards(newCards: ExtendedMediaCardListScrollable) {
            adapter.data = newCards
            initScroll(
                binding.innerRecyclerView,
                binding.innerRecyclerView.layoutManager!! as LinearLayoutManager,
                interactListener,
                newCards
            )
        }

        fun updateLineName(listName: String) {
            binding.animeLineTitleView.text = listName
        }
    }

    interface ExploreMediaRVAdapterListener {
        fun mediaLineClicked(dataLineData: ExtendedMediaCardListScrollable)
        fun mediaItemClicked(data: ExtendedMediaCardListScrollable, mediaCardData: ExtendedMediaCard, position: Int)
        fun requirePaginate(data: ExtendedMediaCardListScrollable)
    }

    private companion object {
        private const val TAG = "ExploreMediaRVAdapter"
    }
}