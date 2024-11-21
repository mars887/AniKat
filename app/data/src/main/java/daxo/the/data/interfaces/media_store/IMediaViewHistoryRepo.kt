package daxo.the.data.interfaces.media_store

import daxo.core.model.media.ExtendedMediaCard
import daxo.core.model.media.ExtendedMediaCardViewed

interface IMediaViewHistoryRepo {
    suspend fun getLastViewed(): ExtendedMediaCardViewed?
    suspend fun getViewedPaged(page: Int, per_page: Int): List<ExtendedMediaCardViewed?>
    suspend fun applyMediaOpened(card: ExtendedMediaCard)
}