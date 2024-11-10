package daxo.the.data.sqlrepo.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import daxo.the.data.sqlrepo.entity.converters.LongToDateConverter
import java.util.Date

@Entity(tableName = "media_view_history")
@TypeConverters(LongToDateConverter::class)
data class MediaViewHistoryEntity(
    @PrimaryKey
    @ColumnInfo(name = "media_id")
    val mediaId: Int,

    @ColumnInfo(name = "_last_viewed")

    val lastViewed: Date,
)