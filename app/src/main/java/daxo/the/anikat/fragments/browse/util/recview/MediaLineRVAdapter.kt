package daxo.the.anikat.fragments.browse.util.recview

import android.graphics.drawable.Animatable2
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import daxo.the.anikat.R
import daxo.the.anikat.databinding.MediaCardItemBinding
import daxo.the.anikat.fragments.browse.data.entity.BasicMediaCardListScrollable
import daxo.the.anikat.fragments.browse.util.diffutil.BasicMediaCardListDiffUtil
import daxo.the.domain.model.media.BasicMediaCard


class MediaLineRVAdapter(
    private val interactListener: ExploreMediaRVAdapter.ExploreMediaRVAdapterListener?
) : RecyclerView.Adapter<MediaLineRVAdapter.MediaCardViewHolder>() {

    var data: BasicMediaCardListScrollable? = null
        set(value) {
            val callback = BasicMediaCardListDiffUtil(field?.basicMediaCardList,value?.basicMediaCardList)
            field = value
            DiffUtil.calculateDiff(callback)
                .dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaCardViewHolder {
        val binding =
            MediaCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MediaCardViewHolder(binding)
    }

    override fun getItemCount(): Int = data?.basicMediaCardList?.cards?.size ?: 0

    override fun onBindViewHolder(holder: MediaCardViewHolder, position: Int) {
        data!!.basicMediaCardList.cards[position].let {
            holder.bind(data!!, it, interactListener, position)
        }
    }

    class MediaCardViewHolder(val binding: MediaCardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            cardsList: BasicMediaCardListScrollable,
            data: BasicMediaCard,
            interactListener: ExploreMediaRVAdapter.ExploreMediaRVAdapterListener?,
            position: Int
        ) {
            // media title
            binding.titleTextView.text = data.title

            // trending Counter
            if(data.averageScope != -1) {
                binding.trendingCounterView.visibility = View.INVISIBLE
            } else {
                binding.trendingCounterView.visibility = View.VISIBLE
                binding.trendingCounterView.text = data.averageScope.toString()
            }

            // poster image
            Glide.with(binding.root)
                .load(data.coverImageEL)
                .placeholder(R.drawable.media_card_placeholder_anim_vector)
                .into(binding.posterImageView)

            // loading animation
            val drawable = binding.posterImageView.drawable
            if(drawable is AnimatedVectorDrawable) {
                drawable.start()

                drawable.registerAnimationCallback(object : Animatable2.AnimationCallback() {
                    override fun onAnimationEnd(none: Drawable) = drawable.start()
                })
            }

            // open media click listener
            binding.posterImageView.setOnClickListener {
                interactListener?.mediaItemClicked(cardsList,data,position)
            }
        }
    }
}