package daxo.the.data.sqlrepo.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import daxo.the.data.sqlrepo.entity.ExtendedMediaCardEntity
import daxo.the.data.sqlrepo.entity.ExtendedMediaCardViewedEntity
import daxo.the.data.sqlrepo.entity.MediaViewHistoryEntity

@Dao
interface MediaViewHistoryDao {

    @Query(
        """
        SELECT 
            bmc.*,
            mvh.lastViewed
        FROM media_view_history AS mvh
        LEFT JOIN extended_media_cache AS bmc ON bmc.mediaId = mvh.mediaId
        ORDER BY mvh.lastViewed DESC
        LIMIT 1
    """
    )
    fun getLastViewedMediaPage(): ExtendedMediaCardViewedEntity?

    @Query(
        """
        SELECT 
            bmc.*,
            mvh.lastViewed AS lastViewed
        FROM media_view_history AS mvh
        LEFT JOIN extended_media_cache AS bmc ON bmc.mediaId = mvh.mediaId
        ORDER BY mvh.lastViewed DESC
        LIMIT :perPage OFFSET (:page * :perPage)
    """
    )
    fun getLastViewedMediaPages(page: Int, perPage: Int): List<ExtendedMediaCardViewedEntity?>

    @Upsert
    fun applyMediaOpened(media: MediaViewHistoryEntity)
}