package daxo.the.anikat.fragments.mediaviewinghistory

import daxo.core.model.media.ExtendedMediaCardViewed

data class MediaHistoryPageListScrollable(
    val cards: List<ExtendedMediaCardViewed>,
    val page: Int = 1
)