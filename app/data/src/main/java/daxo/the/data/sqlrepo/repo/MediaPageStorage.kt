package daxo.the.data.sqlrepo.repo

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import daxo.the.data.sqlrepo.dao.ExtendedMediaCacheDao
import daxo.the.data.sqlrepo.dao.MediaViewHistoryDao
import daxo.the.data.sqlrepo.entity.ExtendedMediaCardEntity
import daxo.the.data.sqlrepo.entity.MediaViewHistoryEntity
import daxo.the.data.sqlrepo.entity.converters.LongToDateConverter
import daxo.the.data.sqlrepo.entity.converters.MinisConverter
import daxo.the.data.sqlrepo.entity.converters.StringToListConverter

@Database(entities = [MediaViewHistoryEntity::class,ExtendedMediaCardEntity::class], version = 1, exportSchema = false)
@TypeConverters(
    StringToListConverter::class,
    LongToDateConverter::class,
    MinisConverter::class
)
abstract class MediaPageStorage: RoomDatabase() {
    abstract fun mediaViewHistoryDao(): MediaViewHistoryDao
    abstract fun extendedMediaCacheDao(): ExtendedMediaCacheDao
}