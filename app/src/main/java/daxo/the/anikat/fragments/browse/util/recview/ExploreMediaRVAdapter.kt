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
import daxo.the.anikat.fragments.browse.util.decorator.MediaLineDecorator
import daxo.the.anikat.fragments.browse.util.diffutil.ExploreMediaDiffUtilImpl
import jp.wasabeef.recyclerview.animators.FadeInAnimator
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExploreMediaRVAdapter(
    private val context: Context,
) : RecyclerView.Adapter<ExploreMediaRVAdapter.MediaLineViewHolder>() {

    var interactListener: ExploreMediaRVAdapterListener? = null

    var data = listOf<MediaLineData>()
        set(value) {
            val callback = ExploreMediaDiffUtilImpl(field, value)
            field = value
            DiffUtil.calculateDiff(callback).dispatchUpdatesTo(this)
            //println("EMRV input   " + field.map { "${it.tag}-${it.unic} " }.joinToString(separator = " "))
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaLineViewHolder {
        val binding =
            ExploreRvTestItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MediaLineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MediaLineViewHolder, position: Int) {
        data[position].let {
            holder.bind(it, context, interactListener)
            //println("updating bind with ${it.tag}-${it.unic}")
        }
    }


    override fun getItemCount(): Int = data.size

    class MediaLineViewHolder(val binding: ExploreRvTestItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var firstInit = true
        private var lastTag: ExploreMediaPagesInfo.MediaTypes? = null

        fun bind(
            data: MediaLineData,
            context: Context,
            interactListener: ExploreMediaRVAdapterListener?,
        ) {
            binding.animeLineTitleView.text = data.lineName

            val recyclerView = binding.innerRecyclerView

            //   println("bind ${data.lineName}-${data.tag} -> old tag $lastTag - finit $firstInit")

            if (firstInit) {
                firstInit = false
                val layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                val adapter = MediaLineRVAdapter(interactListener)

                adapter.data = data
                recyclerView.layoutManager = layoutManager
                recyclerView.adapter = adapter


                if (recyclerView.itemDecorationCount == 0)
                    recyclerView.addItemDecoration(
                        MediaLineDecorator(
                            context.resources.getDimensionPixelSize(
                                R.dimen.exploreFragmentBaseMargin
                            )
                        )
                    )


                initScrollListener(recyclerView, layoutManager, interactListener, data, adapter)

                binding.animeLineTitleView.setOnClickListener {
                    interactListener?.mediaLineClicked(data)
                }

                recyclerView.itemAnimator = FadeInAnimator().apply {
                    moveDuration = 500
                    addDuration = 500
                    changeDuration = 500
                    removeDuration = 500
                }
                //println("restoring ${layoutManager.findFirstVisibleItemPosition()} ${-data.scrollPosition.get()}")
                layoutManager.scrollToPosition(data.scrollPosition.get() )
            } else {
                //println("pre restore 2")
                (recyclerView.adapter as MediaLineRVAdapter).data = data
                if (lastTag != data.tag) {
                    recyclerView.layoutManager?.let {
                        //println("restoring ${(it as LinearLayoutManager).findFirstVisibleItemPosition()} ${-data.scrollPosition.get()}")
                        it.scrollToPosition(data.scrollPosition.get())
                    }
                }
                lastTag = data.tag
            }
        }

        private fun initScrollListener(
            recyclerView: RecyclerView,
            layoutManager: LinearLayoutManager,
            interactListener: ExploreMediaRVAdapterListener?,
            data: MediaLineData,
            adapter: MediaLineRVAdapter
        ) {
            //println("init scrollListener on ${data.lineName}")
            recyclerView.clearOnScrollListeners()
            recyclerView.addOnScrollListener(MediaLineOnScrollListener(layoutManager, {
                requestPaginate(interactListener, data, adapter)
            }, {
                data.scrollPosition.set(layoutManager.findFirstVisibleItemPosition())
                //   println("saving ${data.scrollPosition}")
            }))
        }


        private fun requestPaginate(
            interactListener: ExploreMediaRVAdapterListener?,
            data: MediaLineData,
            adapter: MediaLineRVAdapter
        ) {
            CoroutineScope(
                CoroutineExceptionHandler { coroutineContext, throwable ->
                    println(throwable.message)
                    throwable.printStackTrace()
                }
            ).launch {
                interactListener?.requirePaginate(data) { response, isFromCache -> // TODO
                    withContext(Dispatchers.Main) {

                        val newLineData =
                            MediaLineData(
                                response.lineName,
                                mutableListOf(),
                                response.tag,
                                if (data.tag != ExploreMediaPagesInfo.MediaTypes.EMPTY) data.scrollPosition else adapter.data.scrollPosition
                            )
                        newLineData.data.addAll(adapter.data.data)

                        response.data.forEach { mcd ->
                            val foundId =
                                newLineData.data.indexOfFirst { it.mediaId == mcd.mediaId }

                            if (foundId == -1) newLineData.data += mcd else {
                                newLineData.data[foundId] = mcd
                            }
                        }

                        adapter.data = newLineData
                        data.data = newLineData.data
                    }
                }
            }
        }
    }

    interface ExploreMediaRVAdapterListener {
        fun mediaLineClicked(dataLineData: MediaLineData)
        fun mediaItemClicked(data: MediaLineData, mediaCardData: MediaCardData, position: Int)
        suspend fun requirePaginate(
            data: MediaLineData,
            func: suspend (MediaLineData, Boolean) -> Unit
        )
    }
}