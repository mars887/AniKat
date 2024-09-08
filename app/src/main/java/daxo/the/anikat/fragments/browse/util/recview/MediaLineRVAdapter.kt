package daxo.the.anikat.fragments.browse.util.recview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import daxo.the.anikat.fragments.browse.data.entity.MediaCardData
import daxo.the.anikat.fragments.browse.util.diffutil.MediaLineDiffUtilImpl
import daxo.the.anikat.databinding.MediaCardItemBinding
import daxo.the.anikat.fragments.browse.data.entity.ExploreMediaPagesInfo
import daxo.the.anikat.fragments.browse.data.entity.MediaLineData

class MediaLineRVAdapter(
    private val interactListener: ExploreMediaRVAdapter.ExploreMediaRVAdapterListener?
) : RecyclerView.Adapter<MediaLineRVAdapter.MediaCardViewHolder>() {

    var data: MediaLineData = MediaLineData("", mutableListOf(),ExploreMediaPagesInfo.MediaTypes.EMPTY)
        set(value) {
            val callback = MediaLineDiffUtilImpl(field.data, value.data)
            field = value
            DiffUtil.calculateDiff(callback)
                .dispatchUpdatesTo(this)
            println("media line adapter notified")
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaCardViewHolder {
        val binding =
            MediaCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MediaCardViewHolder(binding)
    }

    override fun getItemCount(): Int = data.data.size

    override fun onBindViewHolder(holder: MediaCardViewHolder, position: Int) {
        data.data[position].let {
            holder.bind(data, it, interactListener, position)
        }
    }

    class MediaCardViewHolder(val binding: MediaCardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            lineData: MediaLineData,
            data: MediaCardData,
            interactListener: ExploreMediaRVAdapter.ExploreMediaRVAdapterListener?,
            position: Int
        ) {
            binding.titleTextView.text = data.title
            binding.trendingCounterView.text = data.averageScore
            Glide.with(binding.root)
                .load(data.coverImageLink)
                .into(binding.posterImageView)

            binding.posterImageView.setOnClickListener {
                interactListener?.mediaItemClicked(lineData,data,position)
            }
        }
    }
}