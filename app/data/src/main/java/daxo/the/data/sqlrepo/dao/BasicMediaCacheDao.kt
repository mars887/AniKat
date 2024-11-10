package daxo.the.data.sqlrepo.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import daxo.the.data.sqlrepo.entity.BasicMediaPageEntity

@Dao
interface BasicMediaCacheDao {

    @Upsert
    fun insertOne(entity: BasicMediaPageEntity)

    @Upsert
    fun insertMany(entities: List<BasicMediaPageEntity>)

    @Update
    fun updateOne(entity: BasicMediaPageEntity)

    @Update
    fun updateMany(entities: List<BasicMediaPageEntity>)

    @Query("SELECT * FROM basic_media_cache WHERE :mediaId == media_id")
    fun getById(mediaId: Int): BasicMediaPageEntity?

    @Query("SELECT * FROM basic_media_cache WHERE media_id IN (:mediaIds)")
    fun getById(mediaIds: List<Int>): List<BasicMediaPageEntity>
}