package daxo.core.model.media.minis

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MediaTitle(
    val userPreferred: String?,
    val romaji: String?,
    val english: String?,
    val native: String?,
): Parcelable