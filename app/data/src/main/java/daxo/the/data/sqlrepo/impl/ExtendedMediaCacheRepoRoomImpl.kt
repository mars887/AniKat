package daxo.the.data.sqlrepo.impl

import daxo.the.data.interfaces.media_store.IExtendedMediaCacheRepo
import daxo.the.data.sqlrepo.dao.ExtendedMediaCacheDao
import daxo.the.data.sqlrepo.entity.ExtendedMediaCardEntity
import daxo.core.model.media.ExtendedMediaCard
import javax.inject.Inject

class ExtendedMediaCacheRepoRoomImpl @Inject constructor(
    private val extendedMediaCacheDao: ExtendedMediaCacheDao
) : IExtendedMediaCacheRepo {
    override suspend fun add(extendedMediaCard: ExtendedMediaCard) {
        extendedMediaCacheDao.insertOne(extendedMediaCard.toEntity())
    }

    override suspend fun add(extendedMediaCardList: List<ExtendedMediaCard>) {
        extendedMediaCacheDao.insertMany(extendedMediaCardList.map { it.toEntity() })
    }

    override suspend fun get(id: Int): ExtendedMediaCard? {
        return extendedMediaCacheDao.getById(id)?.toDomain()
    }

    override suspend fun get(ids: List<Int>): List<ExtendedMediaCard?> {
        return extendedMediaCacheDao.getById(ids).map { it?.toDomain() }
    }

    private fun ExtendedMediaCard.toEntity(): ExtendedMediaCardEntity {
        return ExtendedMediaCardEntity(
            mediaId, title, studios, season, seasonYear, nextAiringEpisode, format, description,
            episodes, genres, averageScore, favourites, coverImage, bannerImage, lastUpdate
        )
    }

    private fun ExtendedMediaCardEntity.toDomain(): ExtendedMediaCard {
        return ExtendedMediaCard(
            mediaId, title, studios, season, seasonYear, nextAiringEpisode, format, description,
            episodes, genres, averageScore, favourites, coverImage, bannerImage, lastUpdate
        )
    }
}


