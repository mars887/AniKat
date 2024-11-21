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
import daxo.the.anikat.databinding.VerticalBasicMediaCardBinding
import daxo.the.anikat.fragments.browse.data.entity.ExtendedMediaCardListScrollable
import daxo.the.anikat.fragments.browse.util.diffutil.BasicMediaCardListDiffUtil
import daxo.core.model.media.ExtendedMediaCard


class MediaLineRVAdapter(
    private val interactListener: ExploreMediaRVAdapter.ExploreMediaRVAdapterListener?
) : RecyclerView.Adapter<MediaLineRVAdapter.MediaCardViewHolder>() {

    var data: ExtendedMediaCardListScrollable? = null
        set(value) {
            val callback = BasicMediaCardListDiffUtil(field?.extendedMediaCardList,value?.extendedMediaCardList)
            field = value
            DiffUtil.calculateDiff(callback)
                .dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaCardViewHolder {
        val binding =
            VerticalBasicMediaCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MediaCardViewHolder(binding)
    }

    override fun getItemCount(): Int = data?.extendedMediaCardList?.cards?.size ?: 0

    override fun onBindViewHolder(holder: MediaCardViewHolder, position: Int) {
        data!!.extendedMediaCardList.cards[position].let {
            holder.bind(data!!, it, interactListener, position)
        }
    }

    class MediaCardViewHolder(val binding: VerticalBasicMediaCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            cardsList: ExtendedMediaCardListScrollable,
            data: ExtendedMediaCard,
            interactListener: ExploreMediaRVAdapter.ExploreMediaRVAdapterListener?,
            position: Int
        ) {
            // media title
            binding.titleTextView.text = data.title?.english

            // trending Counter
            if(data.averageScore != -1) {
                binding.trendingCounterView.visibility = View.INVISIBLE
            } else {
                binding.trendingCounterView.visibility = View.VISIBLE
                binding.trendingCounterView.text = data.averageScore.toString()
            }

            // poster image
            data.coverImage?.large?.let {
                Glide.with(binding.root)
                    .load(it)
                    .placeholder(R.drawable.media_card_placeholder_anim_vector)
                    .into(binding.posterImageView)
            }


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