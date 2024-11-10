package daxo.the.data.sqlrepo.entity

import androidx.room.ColumnInfo
import androidx.room.TypeConverters
import daxo.the.data.sqlrepo.entity.converters.LongToDateConverter
import daxo.the.data.sqlrepo.entity.converters.StringToListConverter
import java.util.Date

@TypeConverters(StringToListConverter::class, LongToDateConverter::class)
data class BasicMediaPageViewedEntity(
    @ColumnInfo(name = "media_id")
    val mediaId: Int,
    val title: String? = null,
    val description: String? = null,
    val episodes: Int? = null,
    val genres: List<String>? = null,
    @ColumnInfo(name = "average_scope")
    val averageScope: Int? = null,
    val favourites: Int? = null,
    val coverImageEL: String? = null,
    val bannerImage: String? = null,
    val lastUpdate: Date? = null,
    val lastViewed: Date
)
