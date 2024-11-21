package daxo.the.data.sqlrepo.entity

import android.os.Parcelable
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
data class ExtendedMediaCardViewedEntity(
    @PrimaryKey val mediaId: Int,
    val title: MediaTitle? = null,
    val studios: Studios? = null,
    val season: MediaSeason? = null,
    val seasonYear: Int? = null,
    val nextAiringEpisode: NextAiringEpisode? = null,
    val format: MediaFormat? = null,
    val description: String? = null,
    val episodes: Int? = null,
    val genres: List<String>? = null,
    val averageScore: Int? = null,
    val favourites: Int? = null,
    val coverImage: CoverImage? = null,
    val bannerImage: String? = null,
    val lastUpdate: Date? = null,
    val lastViewed: Date
): Parcelable