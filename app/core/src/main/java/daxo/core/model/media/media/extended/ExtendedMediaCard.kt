package daxo.core.model.media.media.extended

import android.os.Parcelable
import daxo.core.model.media.enums.MediaFormat
import daxo.core.model.media.enums.MediaSeason
import daxo.core.model.byApollo.media.BasicStudioQuery
import daxo.core.model.byApollo.media.MediaCoverImage
import daxo.core.model.byApollo.media.MediaTitle
import daxo.core.model.byApollo.media.NextAiringEpisode
import daxo.core.model.media.enums.MediaType
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class ExtendedMediaCard(
    val mediaId: Int,
    val mediaType: MediaType?,
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
    val popularity: Int?,
    val coverImage: MediaCoverImage?,
    val bannerImage: String?,
    val lastUpdate: Date,
) : Parcelable