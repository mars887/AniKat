package daxo.the.data.sqlrepo.impl

import daxo.the.data.interfaces.media_store.IBasicMediaCacheRepo
import daxo.the.data.sqlrepo.dao.BasicMediaCacheDao
import daxo.the.domain.model.media.BasicMediaCard
import javax.inject.Inject

class BasicMediaCacheRepoRoomImpl @Inject constructor(
    private val basicMediaCacheDao: BasicMediaCacheDao
): IBasicMediaCacheRepo {
    override fun add(basicMediaCard: BasicMediaCard) {
        TODO("Not yet implemented")
    }

    override fun add(basicMediaCardList: List<BasicMediaCard>) {
        TODO("Not yet implemented")
    }

    override fun get(id: Int): BasicMediaCard {
        TODO("Not yet implemented")
    }

    override fun get(ids: List<Int>): List<Int> {
        TODO("Not yet implemented")
    }
}