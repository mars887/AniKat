package daxo.the.anikat.fragments.mediaviewinghistory

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Animatable2
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.setPadding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import daxo.core.model.media.ExtendedMediaCardViewed
import daxo.the.anikat.R
import daxo.the.anikat.databinding.HorizontalBasicMediaCardBinding
import daxo.the.anikat.tests.TextViewWithDivider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MediaHistoryRVAdapter(
    private val interactListener: MediaHistoryAdapterListener,
    private val context: Context
) : RecyclerView.Adapter<MediaHistoryRVAdapter.MediaCardHolder>() {

    private var parentJob = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Default + parentJob)

    var data: List<ExtendedMediaCardViewed>? = null
        set(value) {
            val callback = MediaHistoryRVDiffUtil(field, value)
            field = value
            DiffUtil.calculateDiff(callback)
                .dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaCardHolder {
        val binding =
            HorizontalBasicMediaCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MediaCardHolder(binding)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        parentJob.cancelChildren()
        parentJob = SupervisorJob()
        scope.coroutineContext[Job]?.let { scope.plus(parentJob) }
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: MediaCardHolder, position: Int) {
        data?.get(position)?.let {
            holder.bind(it, context, scope,interactListener)
        }
    }

    inner class MediaCardHolder(private val binding: HorizontalBasicMediaCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            card: ExtendedMediaCardViewed,
            context: Context,
            scope: CoroutineScope,
            interactListener: MediaHistoryAdapterListener
        ) {
            binding.mediaTitle.text = card.title?.userPreferred ?: card.title?.english

            binding.genresLayout.removeAllViews()
            scope.launch {
                val genresLP = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    val endMargin = binding.root.context.resources.getDimensionPixelSize(R.dimen.smallMargin)
                    setMargins(0, 0, endMargin, 0)
                }
                card.genres?.forEach { genre ->
                    val random = Random(card.mediaId)
                    val genreColor = Color.HSVToColor(
                        floatArrayOf(
                            random.nextInt(0, 360).toFloat(),
                            random.nextInt(20, 40) / 100f,
                            1f
                        )
                    )

                    val view = TextView(context).apply {
                        text = genre
                        setTextColor(resources.getColor(R.color.black))
                        layoutParams = genresLP
                        setPadding(5)
                        textSize = 10f
                        setBackgroundResource(R.drawable.media_card_genre_shape)
                        backgroundTintList = ColorStateList.valueOf(genreColor)
                        gravity = Gravity.CENTER
                        typeface = resources.getFont(R.font.ubuntu_medium)
                    }
                    withContext(Dispatchers.Main) {
                        binding.genresLayout.addView(view)
                    }
                }
            }

            binding.infoLayout.apply {
                removeAllViews()
                scope.launch {
                    val lp = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                    )
                    lp.setMargins(0, 0, 5, 0)

                    val data = listOf(
                        "Episodes\n${card.episodes}",
                        "Studio\n${card.studios?.edges?.firstOrNull { it.isMain ?: false }?.name}",
                        "Season\n${card.season}",
                        "Favourites\n${card.favourites}",
                        "Format\n${card.format?.value}",
                        "Score\n${card.averageScore}"
                    ).filter {
                        "\nnull" !in it
                    }.map {
                        TextViewWithDivider(context).apply {
                            text = it
                            background = AppCompatResources.getDrawable(context, R.drawable.tag_value_background_shape)
                            layoutParams = lp
                            setPadding(15, 5, 15, 5)
                            gravity = Gravity.CENTER
                            typeface = resources.getFont(R.font.ubuntu_medium)
                            setTextColor(resources.getColor(R.color.black))
                        }
                    }
                    withContext(Dispatchers.Main) {
                        data.forEach {
                            this@apply.addView(it)
                        }
                    }
                }
            }

            card.coverImage?.large?.let {
                Glide.with(binding.root)
                    .load(it)
                    .placeholder(R.drawable.media_card_placeholder_anim_vector)
                    .into(binding.posterImageView)
            }


            val drawable = binding.posterImageView.drawable
            if (drawable is AnimatedVectorDrawable) {
                drawable.start()

                drawable.registerAnimationCallback(object : Animatable2.AnimationCallback() {
                    override fun onAnimationEnd(none: Drawable) = drawable.start()
                })
            }

            listOf(
                binding.mediaTitle,
                binding.posterImageView,
                binding.infoLayout,
                binding.genresLayout
            ).forEach { it ->
                it.setOnClickListener {
                    interactListener(card)
                }

            }
        }
    }

    interface MediaHistoryAdapterListener {
        /**
         * on card clicked
         */
        operator fun invoke(card: ExtendedMediaCardViewed)
        /**
         * on card removed from watced
         */
        //fun removeCard(card: ExtendedMediaCardViewed)
    }
}
