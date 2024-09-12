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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
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
        fun bind(
            data: MediaLineData,
            context: Context,
            interactListener: ExploreMediaRVAdapterListener?
        ) {
            binding.animeLineTitleView.text = data.lineName

            val recyclerView = binding.innerRecyclerView
            val layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            val adapter = MediaLineRVAdapter(interactListener)

            adapter.data = data
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = adapter
            recyclerView.addItemDecoration(
                MediaLineDecorator(
                    context.resources.getDimensionPixelSize(
                        R.dimen.exploreFragmentBaseMargin
                    )
                )
            )
            recyclerView.addOnScrollListener(MediaLineOnScrollListener(layoutManager) {
                CoroutineScope(EmptyCoroutineContext).launch {
                    interactListener?.requirePaginate(data)?.collect {
                        val dt = mutableListOf<MediaCardData>()
                        dt.addAll(adapter.data.data)
                        dt.addAll(it.data)

                        withContext(Dispatchers.Main) {
                            adapter.data = MediaLineData(
                                it.lineName,
                                dt, it.tag
                            )
                        }

                    }
                }
            })

            binding.animeLineTitleView.setOnClickListener {
                interactListener?.mediaLineClicked(data)
            }
        }
    }

    interface ExploreMediaRVAdapterListener {
        fun mediaLineClicked(dataLineData: MediaLineData)
        fun mediaItemClicked(data: MediaLineData, mediaCardData: MediaCardData, position: Int)
        suspend fun requirePaginate(data: MediaLineData): Flow<MediaLineData>
    }
}


/*
val layoutManager = LinearLayoutManager(this.requireContext())
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = ExploreMediaRVAdapter()

        binding.recyclerView.addItemDecoration(CenteredRVDecorator())
 */