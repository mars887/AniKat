package daxo.the.data.sqlrepo.dao

import androidx.room.Dao
import androidx.room.Query
import daxo.the.data.sqlrepo.entity.BasicMediaPageEntity
import daxo.the.data.sqlrepo.entity.BasicMediaPageViewedEntity

@Dao
interface MediaViewHistoryDao {

    @Query("""
        SELECT 
            bmc.media_id,
            bmc.title,
            bmc.description,
            bmc.episodes,
            bmc.genres,
            bmc.average_scope,
            bmc.favourites,
            bmc.coverImageEL,
            bmc.bannerImage,
            bmc._last_update AS lastUpdate,
            mvh._last_viewed AS lastViewed
        FROM media_view_history AS mvh
        LEFT JOIN basic_media_cache AS bmc ON bmc.media_id = mvh.media_id
        ORDER BY mvh._last_viewed DESC
        LIMIT 1
    """)
    fun getLastViewedMediaPage(): BasicMediaPageViewedEntity

    @Query("""
        SELECT 
            bmc.media_id,
            bmc.title,
            bmc.description,
            bmc.episodes,
            bmc.genres,
            bmc.average_scope,
            bmc.favourites,
            bmc.coverImageEL,
            bmc.bannerImage,
            bmc._last_update AS lastUpdate,
            mvh._last_viewed AS lastViewed
        FROM media_view_history AS mvh
        LEFT JOIN basic_media_cache AS bmc ON bmc.media_id = mvh.media_id
        ORDER BY mvh._last_viewed DESC
        LIMIT :perPage OFFSET (:page * :perPage)
    """)
    fun getLastViewedMediaPages(page: Int,perPage: Int): List<BasicMediaPageViewedEntity>
}