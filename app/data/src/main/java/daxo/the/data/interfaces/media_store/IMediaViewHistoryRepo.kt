package daxo.the.data.interfaces.media_store

import daxo.the.domain.model.media.BasicMediaCardViewed

interface IMediaViewHistoryRepo {
    fun getLastViewed(): BasicMediaCardViewed
    fun getViewedPaged(page: Int, per_page: Int): BasicMediaCardViewed
}