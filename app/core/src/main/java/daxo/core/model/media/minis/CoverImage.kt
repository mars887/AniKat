package daxo.core.model.media.minis

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CoverImage(
    val extraLarge: String?,
    val large: String?,
): Parcelable