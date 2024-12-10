package daxo.core.model.byApollo.media

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FuzzyDate(
    val day: Int?,
    val month: Int?,
    val year: Int?
) : Parcelable