package daxo.core.model.byApollo.media

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BasicReviewQuery(
    val reviews: List<BasicReview>
): Parcelable

@Parcelize
data class BasicReview(
    val id: Int?,
    val userId: Int?,
    val summary: String?,
    val score: Int?,
    val user: BasicUser?
): Parcelable