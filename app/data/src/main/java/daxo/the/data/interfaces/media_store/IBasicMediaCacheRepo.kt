package daxo.the.data.interfaces.media_store

import daxo.the.domain.model.media.BasicMediaCard

interface IBasicMediaCacheRepo {
    fun add(basicMediaCard: BasicMediaCard)
    fun add(basicMediaCardList: List<BasicMediaCard>)
    fun get(id: Int): BasicMediaCard
    fun get(ids: List<Int>): List<Int>
}