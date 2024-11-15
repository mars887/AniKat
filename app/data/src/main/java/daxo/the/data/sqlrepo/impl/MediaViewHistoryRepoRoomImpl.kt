package daxo.the.data.sqlrepo.impl

import daxo.the.data.interfaces.media_store.IMediaViewHistoryRepo
import daxo.the.data.sqlrepo.dao.MediaViewHistoryDao
import daxo.the.domain.model.media.BasicMediaCardViewed
import javax.inject.Inject

class MediaViewHistoryRepoRoomImpl @Inject constructor(
    private val mediaViewHistoryDao: MediaViewHistoryDao
) : IMediaViewHistoryRepo {
    override fun getLastViewed(): BasicMediaCardViewed {
        TODO("Not yet implemented")
    }

    override fun getViewedPaged(page: Int, per_page: Int): BasicMediaCardViewed {
        TODO("Not yet implemented")
    }
}