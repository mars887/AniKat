package daxo.core.model.byApollo.media

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BasicUser(
    val id: Int,
    val name: String,
    val avatar: UserAvatar?
): Parcelable

@Parcelize
data class UserAvatar(
    val large: String?,
    val medium: String?,
): Parcelable