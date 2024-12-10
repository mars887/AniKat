package daxo.core.model.byApollo.media

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BasicRecommendationsQuery(
    val media: List<BasicMedia>
): Parcelable