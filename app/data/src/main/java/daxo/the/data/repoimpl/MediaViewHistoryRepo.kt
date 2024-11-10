package daxo.the.data.repoimpl

import daxo.the.domain.irepo.IMediaViewHistoryRepo
import daxo.the.domain.model.BasicMediaPageViewed

class MediaViewHistoryRepo: IMediaViewHistoryRepo {
    override fun getLastViewed(): BasicMediaPageViewed {
        TODO("Not yet implemented")
    }

    override fun getViewedPaged(page: Int, per_page: Int): BasicMediaPageViewed {
        TODO("Not yet implemented")
    }
}