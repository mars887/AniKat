package daxo.core.model.byApollo.media

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NextAiringEpisode(
    val id: Int,
    val airingAt: Int,
    val timeUntilAiring: Int,
    val episode: Int,
    val mediaId: Int,
    val media: BasicMedia?
) : Parcelable