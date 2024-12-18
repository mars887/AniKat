package daxo.the.data.sqlrepo.impl

import daxo.the.data.interfaces.media_store.IMediaViewHistoryRepo
import daxo.the.data.sqlrepo.dao.MediaViewHistoryDao
import daxo.the.data.sqlrepo.entity.ExtendedMediaCardViewedEntity
import daxo.the.data.sqlrepo.entity.MediaViewHistoryEntity
import daxo.core.model.media.media.extended.ExtendedMediaCard
import daxo.core.model.media.media.extended.ExtendedMediaCardViewed
import java.util.Date
import javax.inject.Inject

class MediaViewHistoryRepoRoomImpl @Inject constructor(
    private val mediaViewHistoryDao: MediaViewHistoryDao
) : IMediaViewHistoryRepo {
    override suspend fun getLastViewed(): ExtendedMediaCardViewed? {
        return mediaViewHistoryDao.getLastViewedMediaPage()?.toDomain()
    }

    override suspend fun getViewedPaged(page: Int, per_page: Int): List<ExtendedMediaCardViewed?> {
        return mediaViewHistoryDao.getLastViewedMediaPages(page, per_page).map { it?.toDomain() }
    }

    override suspend fun applyMediaOpened(card: ExtendedMediaCard) {
        mediaViewHistoryDao.applyMediaOpened(card.toViewHistory())
    }

    private fun ExtendedMediaCardViewedEntity.toDomain(): ExtendedMediaCardViewed {
        return ExtendedMediaCardViewed(
            mediaId, mediaType, title, studios, season, seasonYear, nextAiringEpisode, format, description,
            episodes, genres, averageScore, favourites, popularity, coverImage, bannerImage, lastUpdate, lastViewed
        )
    }

    private fun ExtendedMediaCard.toViewHistory(): MediaViewHistoryEntity {
        return MediaViewHistoryEntity(mediaId = mediaId, lastViewed = Date())
    }
}



