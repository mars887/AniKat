package daxo.core.model.byApollo.media

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BasicStudio(
    val id: Int,
    val name: String,
    val isFavorite: Boolean
) : Parcelable

@Parcelize
data class BasicStudioConnection(
    val id: Int?,
    val isMain: Boolean?,
    val studio: BasicStudio?
) : Parcelable

@Parcelize
data class BasicStudioQuery(
    val studios: List<BasicStudioConnection>
) : Parcelable