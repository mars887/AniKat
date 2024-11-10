package daxo.the.data.sqlrepo.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import daxo.the.data.sqlrepo.entity.converters.LongToDateConverter
import daxo.the.data.sqlrepo.entity.converters.StringToListConverter
import java.util.Date

@Entity(tableName = "basic_media_cache")
@TypeConverters(StringToListConverter::class, LongToDateConverter::class)
data class BasicMediaPageEntity(
    @PrimaryKey
    @ColumnInfo(name = "media_id")
    val mediaId: Int,
    val title: String,
    val description: String,
    val episodes: Int,

    val genres: List<String>,
    @ColumnInfo(name = "average_scope")
    val averageScope: Int,
    val favourites: Int,
    val coverImageEL: String,
    val bannerImage: String,

    @ColumnInfo(name = "_last_update")
    val lastUpdate: Date,
)