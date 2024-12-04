package daxo.the.data.sqlrepo.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import daxo.the.data.sqlrepo.entity.converters.*
import daxo.core.model.media.enums.MediaFormat
import daxo.core.model.media.enums.MediaSeason
import daxo.core.model.media.minis.CoverImage
import daxo.core.model.media.minis.MediaTitle
import daxo.core.model.media.minis.NextAiringEpisode
import daxo.core.model.media.minis.Studios
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
@Entity(tableName = "extended_media_cache")
data class ExtendedMediaCardEntity(
    @PrimaryKey val mediaId: Int,
    val title: MediaTitle?,
    val studios: Studios?,
    val season: MediaSeason?,
    val seasonYear: Int?,
    val nextAiringEpisode: NextAiringEpisode?,
    val format: MediaFormat?,
    val description: String?,
    val episodes: Int?,
    val genres: List<String>?,
    val averageScore: Int?,
    val favourites: Int?,
    val coverImage: CoverImage?,
    val bannerImage: String?,
    val lastUpdate: Date,
) : Parcelable