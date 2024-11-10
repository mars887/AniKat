package daxo.the.domain.irepo

import daxo.the.domain.model.BasicMediaPageViewed

interface IMediaViewHistoryRepo {
    fun getLastViewed(): BasicMediaPageViewed
    fun getViewedPaged(page: Int, per_page: Int): BasicMediaPageViewed
}