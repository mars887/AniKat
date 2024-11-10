package daxo.the.data.sqlrepo.repo

import androidx.room.Database
import androidx.room.RoomDatabase
import daxo.the.data.sqlrepo.dao.BasicMediaCacheDao
import daxo.the.data.sqlrepo.dao.MediaViewHistoryDao
import daxo.the.data.sqlrepo.entity.BasicMediaPageEntity
import daxo.the.data.sqlrepo.entity.MediaViewHistoryEntity

@Database(entities = [BasicMediaPageEntity::class,MediaViewHistoryEntity::class], version = 1)
abstract class MediaPageStorage: RoomDatabase() {
    abstract fun basicMediaCacheDao(): BasicMediaCacheDao
    abstract fun mediaViewHistoryDao(): MediaViewHistoryDao
}