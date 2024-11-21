package daxo.the.data.sqlrepo.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import daxo.the.data.sqlrepo.entity.converters.LongToDateConverter
import java.util.Date

@Entity(tableName = "media_view_history")
data class MediaViewHistoryEntity(

    @PrimaryKey
    val mediaId: Int,

    val lastViewed: Date,
)