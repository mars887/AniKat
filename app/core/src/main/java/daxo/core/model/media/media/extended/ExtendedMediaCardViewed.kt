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
data class ExtendedMediaCardViewed(
    val mediaId: Int,
    val mediaType: MediaType? = null,
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
    val popularity: Int? = null,
    val coverImage: MediaCoverImage? = null,
    val bannerImage: String? = null,
    val lastUpdate: Date? = null,
    val lastViewed: Date
) : Parcelable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ExtendedMediaCardViewed

        return mediaId == other.mediaId
    }

    override fun hashCode(): Int {
        return mediaId
    }

    fun toExtendedMediaCard(): ExtendedMediaCard {
        return ExtendedMediaCard(
            mediaId, mediaType, title, studios, season, seasonYear, nextAiringEpisode, format, description,
            episodes, genres, averageScore, favourites, popularity, coverImage, bannerImage, lastUpdate ?: Date()
        )
    }
}