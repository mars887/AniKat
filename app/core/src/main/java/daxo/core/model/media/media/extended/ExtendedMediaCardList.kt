package daxo.core.model.media.media.extended

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExtendedMediaCardList(
    val requestData: String,
    val listName: String,
    val cards: List<ExtendedMediaCard>,
): Parcelable