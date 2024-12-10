package daxo.the.data.interfaces.media_store

import daxo.core.model.media.media.extended.ExtendedMediaCard


interface IExtendedMediaCacheRepo {
    suspend fun add(extendedMediaCard: ExtendedMediaCard)
    suspend fun add(extendedMediaCardList: List<ExtendedMediaCard>)
    suspend fun get(id: Int): ExtendedMediaCard?
    suspend fun get(ids: List<Int>): List<ExtendedMediaCard?>
}