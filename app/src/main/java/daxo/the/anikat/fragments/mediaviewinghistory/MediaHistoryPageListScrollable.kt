package daxo.the.anikat.fragments.mediaviewinghistory

import daxo.core.model.media.media.extended.ExtendedMediaCardViewed

data class MediaHistoryPageListScrollable(
    val cards: List<ExtendedMediaCardViewed>,
    val page: Int = 1
)