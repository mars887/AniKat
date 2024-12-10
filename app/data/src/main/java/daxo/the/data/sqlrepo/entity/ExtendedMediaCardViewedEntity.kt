package daxo.the.data.sqlrepo.entity

import android.os.Parcelable
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
data class ExtendedMediaCardViewedEntity(
    @PrimaryKey val mediaId: Int,
    val title: MediaTitle? = null,
    val studios: BasicStudioQuery? = null,
    val season: MediaSeason? = null,
    val seasonYear: Int? = null,
    val nextAiringEpisode: NextAiringEpisode? = null,
    val format: MediaFormat? = null,
    val description: String? = null,
    val episodes: Int? = null,
    val genres: List<String>? = null,
    val averageScore: Int? = null,
    val favourites: Int? = null,
    val coverImage: MediaCoverImage? = null,
    val bannerImage: String? = null,
    val lastUpdate: Date? = null,
    val lastViewed: Date
): Parcelable