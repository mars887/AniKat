package daxo.the.data.sqlrepo.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import daxo.core.model.media.enums.MediaFormat
import daxo.core.model.media.enums.MediaSeason
import daxo.core.model.byApollo.media.BasicStudioQuery
import daxo.core.model.byApollo.media.MediaCoverImage
import daxo.core.model.byApollo.media.MediaTitle
import daxo.core.model.byApollo.media.NextAiringEpisode
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
@Entity(tableName = "extended_media_cache")
data class ExtendedMediaCardEntity(
    @PrimaryKey val mediaId: Int,
    val title: MediaTitle?,
    val studios: BasicStudioQuery?,
    val season: MediaSeason?,
    val seasonYear: Int?,
    val nextAiringEpisode: NextAiringEpisode?,
    val format: MediaFormat?,
    val description: String?,
    val episodes: Int?,
    val genres: List<String>?,
    val averageScore: Int?,
    val favourites: Int?,
    val coverImage: MediaCoverImage?,
    val bannerImage: String?,
    val lastUpdate: Date,
) : Parcelable