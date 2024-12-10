package daxo.core.images

import android.os.Parcel
import android.os.Parcelable
import daxo.core.model.helpers.ImageQGrades
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageInfoModel(
    val mediaTitle: String,
    val loadedImageUrl: String?,
    val qualityUrl: ImageQGrades,
    val mediaId: Int,
) : Parcelable