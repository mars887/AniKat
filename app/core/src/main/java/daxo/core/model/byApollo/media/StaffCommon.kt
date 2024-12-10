package daxo.core.model.byApollo.media

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BasicStaffQuery(
    val staff: List<BasicStaff>?
) : Parcelable

@Parcelize
data class BasicStaff(
    val id: Int,
    val name: BasicStaffName?,
    val language: String?,
    val image: StaffImage?
) : Parcelable


@Parcelize
data class StaffImage(
    val large: String?,
    val medium: String?,
) : Parcelable

@Parcelize
data class BasicStaffName(
    val first: String?,
    val last: String?,
    val full: String?,
) : Parcelable