package daxo.core.images

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageInfoModel(
    val mediaTitle: String,
    val loadedImageUrl: String?,
    val qualityUrl: ImageQualityGrades,
    val mediaId: Int,
) : Parcelable {
    constructor(mediaTitle: String, loadedImageUrl: String?, qualityUrl: IImageQualityUrl, mediaId: Int) :
            this(mediaTitle, loadedImageUrl, qualityUrl.toQG(), mediaId)
}

@Parcelize
data class ImageQualityGrades(
    val max: String?,
    val middle: String?,
    val min: String?,
) : Parcelable {
    constructor(qualityUrl: IImageQualityUrl) : this(qualityUrl.maxPresent(), qualityUrl.middle(), qualityUrl.min())
}

/**
 * qualities for image models:
 * max, middle, min
 */
interface IImageQualityUrl : Parcelable {
    fun max(): String?
    fun middle(): String?
    fun min(): String?

    companion object {
        fun getByOneUrl(url: String): IImageQualityUrl {
            return object : IImageQualityUrl {
                override fun max(): String = url
                override fun middle(): String = url
                override fun min(): String = url
                override fun describeContents(): Int = 0
                override fun writeToParcel(dest: Parcel, flags: Int) {}
            }
        }
    }
}

fun IImageQualityUrl.maxPresent(): String? {
    return max() ?: middle() ?: min()
}

fun IImageQualityUrl.toParcelableString(): String {
    return "${max()}~!~${middle()}~!~${min()}"
}

fun IImageQualityUrl.toQG(): ImageQualityGrades = ImageQualityGrades(this)