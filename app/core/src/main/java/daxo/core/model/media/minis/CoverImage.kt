package daxo.core.model.media.minis

import android.os.Parcelable
import daxo.core.images.IImageQualityUrl
import kotlinx.parcelize.Parcelize

@Parcelize
data class CoverImage(
    val extraLarge: String?,
    val large: String?,
): Parcelable, IImageQualityUrl {
    override fun max(): String? = extraLarge ?: large
    override fun middle(): String? = large
    override fun min(): String? = large
}