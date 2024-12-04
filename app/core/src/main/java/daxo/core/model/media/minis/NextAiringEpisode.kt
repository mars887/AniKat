package daxo.core.model.media.minis

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NextAiringEpisode(
    val airingAt: Int,
    val timeUntilAiring: Int,
    val episode: Int,
): Parcelable