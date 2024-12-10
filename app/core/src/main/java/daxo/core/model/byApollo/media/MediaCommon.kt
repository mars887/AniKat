package daxo.core.model.byApollo.media

import android.os.Parcelable
import daxo.core.model.media.enums.ExternalLinkType
import daxo.core.model.media.enums.MediaFormat
import daxo.core.model.media.enums.MediaListStatus
import daxo.core.model.media.enums.MediaRankType
import daxo.core.model.media.enums.MediaRelationType
import daxo.core.model.media.enums.MediaSeason
import kotlinx.parcelize.Parcelize

@Parcelize
data class BasicMedia(
    val id: Int,
    val title: MediaTitle?,
    val coverImage: MediaCoverImage?
) : Parcelable

@Parcelize
data class MediaCoverImage(
    val extraLarge: String?,
    val large: String?,
    val medium: String?,
    val color: String?,
) : Parcelable

@Parcelize
data class MediaTitle(
    val userPreferred: String?,
    val romaji: String?,
    val english: String?,
    val native: String?,
) : Parcelable

@Parcelize
data class MediaExternalLink(
    val id: Int?,
    val url: String?,
    val site: String?,
    val siteId: Int?,
    val type: ExternalLinkType?,
    val language: String?,
    val color: String?,
    val icon: String?,
    val notes: String?,
    val isDisabled: Boolean?
) : Parcelable

@Parcelize
data class MediaRanking(
    val id: Int?,
    val rank: Int?,
    val type: MediaRankType?,
    val format: MediaFormat?,
    val year: Int?,
    val season: MediaSeason?,
    val allTime: Boolean?,
    val context: String?
) : Parcelable

@Parcelize
data class BasicMediaRelation(
    val id: Int?,
    val media: BasicMedia?,
    val relationType: MediaRelationType?
) : Parcelable

@Parcelize
data class MediaTag(
    val id: Int?,
    val name: String?,
    val description: String?,
    val category: String?,
    val rank: Int?,
    val isGeneralSpoiler: Boolean?,
    val isMediaSpoiler: Boolean?,
    val isAdult: Boolean?,
) : Parcelable

@Parcelize
data class MediaTrailer(
    val id: String?,
    val site: String?,
    val thumbnail: String?,
) : Parcelable

@Parcelize
data class BasicStats(
    /** score and amount */
    val scoreDistribution: List<Pair<Int, Int>>?,
    /** status and amount */
    val statusDistribution: List<Pair<MediaListStatus, Int>>?
) : Parcelable

@Parcelize
data class MediaStreamingEpisode(
    val title: String?,
    val thumbnail: String?,
    val url: String?,
    val site: String?
) : Parcelable