package daxo.core.model.media.media.full

import android.os.Parcelable
import daxo.core.model.media.enums.MediaFormat
import daxo.core.model.media.enums.MediaSeason
import daxo.core.model.media.enums.MediaSource
import daxo.core.model.media.enums.MediaStatus
import daxo.core.model.media.enums.MediaType
import daxo.core.model.byApollo.media.BasicCharacterQuery
import daxo.core.model.byApollo.media.BasicMedia
import daxo.core.model.byApollo.media.BasicMediaRelation
import daxo.core.model.byApollo.media.BasicReview
import daxo.core.model.byApollo.media.BasicStaff
import daxo.core.model.byApollo.media.BasicStats
import daxo.core.model.byApollo.media.BasicStudioConnection
import daxo.core.model.byApollo.media.FuzzyDate
import daxo.core.model.byApollo.media.MediaCoverImage
import daxo.core.model.byApollo.media.MediaExternalLink
import daxo.core.model.byApollo.media.MediaRanking
import daxo.core.model.byApollo.media.MediaTag
import daxo.core.model.byApollo.media.MediaTitle
import daxo.core.model.byApollo.media.MediaTrailer
import daxo.core.model.byApollo.media.NextAiringEpisode
import daxo.core.model.byApollo.media.MediaStreamingEpisode
import kotlinx.parcelize.Parcelize

@Parcelize
data class FullMediaCard(
    /**
     * The id of the media
     */
    public val id: Int,
    /**
     * The type of the media; anime or manga
     */
    public val mediaType: MediaType?,
    /**
     * The format the media was released in
     */
    public val mediaFormat: MediaFormat?,
    /**
     * The current releasing status of the media
     */
    public val mediaStatus: MediaStatus?,
    /**
     * Short description of the media's story and characters
     */
    public val description: String?,
    /**
     * The season the media was initially released in
     */
    public val mediaSeason: MediaSeason?,
    /**
     * The season year the media was initially released in
     */
    public val seasonYear: Int?,
    /**
     * The amount of episodes the anime has when complete
     */
    public val episodes: Int?,
    /**
     * The general length of each anime episode in minutes
     */
    public val duration: Int?,
    /**
     * The amount of chapters the manga has when complete
     */
    public val chapters: Int?,
    /**
     * The amount of volumes the manga has when complete
     */
    public val volumes: Int?,
    /**
     * Where the media was created. (ISO 3166-1 alpha-2)
     */
    public val countryOfOrigin: String?,
    /**
     * If the media is officially licensed or a self-published doujin release
     */
    public val isLicensed: Boolean?,
    /**
     * Source type the media was adapted from.
     */
    public val source: MediaSource?,
    /**
     * Official Twitter hashtags for the media
     */
    public val hashtag: String?,
    /**
     * When the media's data was last updated
     */
    public val updatedAt: Int?,
    /**
     * The banner image of the media
     */
    public val bannerImage: String?,
    /**
     * The genres of the media
     */
    public val genres: List<String>?,
    /**
     * Alternative titles of the media
     */
    public val synonyms: List<String>?,
    /**
     * A weighted average score of all the user's scores of the media
     */
    public val averageScore: Int?,
    /**
     * Mean score of all the user's scores of the media
     */
    public val meanScore: Int?,
    /**
     * The number of users with the media on their list
     */
    public val popularity: Int?,
    /**
     * Locked media may not be added to lists our favorited. This may be due to the entry pending
     * for deletion or other reasons.
     */
    public val isLocked: Boolean?,
    /**
     * The amount of related activity in the past hour
     */
    public val trending: Int?,
    /**
     * The amount of user's who have favourited the media
     */
    public val favourites: Int?,
    /**
     * If the media is marked as favourite by the current authenticated user
     */
    public val isFavourite: Boolean,
    /**
     * If the media is blocked from being added to favourites
     */
    public val isFavouriteBlocked: Boolean,
    /**
     * The url for the media page on the AniList website
     */
    public val siteUrl: String?,
    /**
     * If the media should have forum thread automatically created for it on airing episode release
     */
    public val autoCreateForumThread: Boolean?,
    /**
     * If the media is blocked from being recommended to/from
     */
    public val isRecommendationBlocked: Boolean?,
    /**
     * If the media is blocked from being reviewed
     */
    public val isReviewBlocked: Boolean?,
    /**
     * Notes for site moderators
     */
    public val modNotes: String?,
    /**
     * If the media is intended only for 18+ adult audiences
     */
    public val isAdult: Boolean?,
    /**
     * The official titles of the media in various languages
     */
    public val title: MediaTitle?,
    /**
     * The first official release date of the media
     */
    public val startDate: FuzzyDate?,
    /**
     * The last official release date of the media
     */
    public val endDate: FuzzyDate?,
    /**
     * Media trailer or advertisement
     */
    public val trailer: MediaTrailer?,
    /**
     * The cover images of the media
     */
    public val fullCoverImage: MediaCoverImage?,
    /**
     * List of tags that describes elements and themes of the media
     */
    public val tags: List<MediaTag>?,
    /**
     * Other media in the same or connecting franchise
     */
    public val relations: List<BasicMediaRelation>?,
    /**
     * The characters in the media
     */
    public val characters: List<BasicCharacterQuery>?,
    /**
     * The staff who produced the media
     */
    public val staff: List<BasicStaff>?,
    /**
     * The companies who produced the media
     */
    public val studios: List<BasicStudioConnection>?,
    /**
     * The media's next episode airing schedule
     */
    public val nextAiringEpisode: NextAiringEpisode?,
    /**
     * External links to another site related to the media
     */
    public val externalMediaLinks: List<MediaExternalLink>?,
    /**
     * User recommendations for similar media
     */
    public val recommendations: List<BasicMedia>?,
    /**
     * User reviews of the media
     */
    public val reviews: List<BasicReview>?,
    public val stats: BasicStats?,
    /**
     * The media's entire airing schedule
     */
    public val airingSchedule: List<NextAiringEpisode>?,
    /**
     * Data and links to legal streaming episodes on external sites
     */
    public val mediaStreamingEpisodes: List<MediaStreamingEpisode>?,
    /**
     * The ranking of the media in a particular time span and format compared to other media
     */
    public val rankings: List<MediaRanking>?,
) : Parcelable
