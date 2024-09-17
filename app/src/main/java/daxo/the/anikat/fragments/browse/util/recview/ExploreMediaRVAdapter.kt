package daxo.the.anikat.fragments.browse.util.recview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import daxo.the.anikat.R
import daxo.the.anikat.fragments.browse.data.entity.MediaLineData
import daxo.the.anikat.fragments.browse.util.diffutil.ExploreMediaDiffUtilImpl
import daxo.the.anikat.fragments.browse.util.decorator.MediaLineDecorator
import daxo.the.anikat.databinding.ExploreRvTestItemBinding
import daxo.the.anikat.fragments.browse.data.entity.ExploreMediaPagesInfo
import daxo.the.anikat.fragments.browse.data.entity.MediaCardData
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.EmptyCoroutineContext


class ExploreMediaRVAdapter(
    private val context: Context,
) : RecyclerView.Adapter<ExploreMediaRVAdapter.MediaLineViewHolder>() {

    var interactListener: ExploreMediaRVAdapterListener? = null

    var data = listOf<MediaLineData>()
        set(value) {
            val callback = ExploreMediaDiffUtilImpl(field, value)
            field = value
            DiffUtil.calculateDiff(callback).dispatchUpdatesTo(this)
            println("explore adapter notified")
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaLineViewHolder {
        val binding =
            ExploreRvTestItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MediaLineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MediaLineViewHolder, position: Int) {
        data[position].let {
            holder.bind(it, context, interactListener)
        }
    }


    override fun getItemCount(): Int = data.size

    class MediaLineViewHolder(val binding: ExploreRvTestItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var lastTag: ExploreMediaPagesInfo.MediaTypes? = null

        fun bind(
            data: MediaLineData,
            context: Context,
            interactListener: ExploreMediaRVAdapterListener?,
        ) {
            binding.animeLineTitleView.text = data.lineName

            val recyclerView = binding.innerRecyclerView

            if (recyclerView.adapter != null && lastTag == data.tag) {
                (recyclerView.adapter as MediaLineRVAdapter).data = data
            } else {
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


                recyclerView.addOnScrollListener(MediaLineOnScrollListener(layoutManager) {
                    requestPaginate(interactListener, data, adapter)
                })

                binding.animeLineTitleView.setOnClickListener {
                    interactListener?.mediaLineClicked(data)
                }
            }
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
                            MediaLineData(response.lineName, mutableListOf(), response.tag)
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