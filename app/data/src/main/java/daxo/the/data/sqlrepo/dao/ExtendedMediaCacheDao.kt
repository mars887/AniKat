package daxo.the.data.sqlrepo.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import daxo.the.data.sqlrepo.entity.ExtendedMediaCardEntity

@Dao
interface ExtendedMediaCacheDao {
    @Upsert
    fun insertOne(entity: ExtendedMediaCardEntity)

    @Upsert
    fun insertMany(entities: List<ExtendedMediaCardEntity>)

    @Update
    fun updateOne(entity: ExtendedMediaCardEntity)

    @Update
    fun updateMany(entities: List<ExtendedMediaCardEntity>)

    @Query("SELECT * FROM extended_media_cache WHERE :mediaId == mediaId")
    fun getById(mediaId: Int): ExtendedMediaCardEntity?

    @Query("SELECT * FROM extended_media_cache WHERE mediaId IN (:mediaIds)")
    fun getById(mediaIds: List<Int>): List<ExtendedMediaCardEntity?>
}