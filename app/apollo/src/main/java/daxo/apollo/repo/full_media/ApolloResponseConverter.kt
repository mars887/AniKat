package daxo.apollo.repo.full_media

import daxo.apollo.repo.converters.AiringScheduleCommonConverter.toDomain
import daxo.apollo.repo.converters.CharacterCommonConverter.toDomain
import daxo.apollo.repo.converters.FuzzyDateCommonConverter.toDomain
import daxo.apollo.repo.converters.MediaCommonConverter.toDomain
import daxo.apollo.repo.converters.ReviewsCommonConverter.toDomain
import daxo.apollo.repo.converters.StaffCommonConverter.toDomain
import daxo.apollo.repo.converters.StudiosCommonConverter.toDomain
import daxo.apollo.repo.converters.enums.CharacterRoleConverter.toDomain
import daxo.apollo.repo.converters.enums.MediaFormatConverter.toDomain
import daxo.apollo.repo.converters.enums.MediaSeasonConverter.toDomain
import daxo.apollo.repo.converters.enums.MediaSourceConverter.toDomain
import daxo.apollo.repo.converters.enums.MediaStatusConverter.toDomain
import daxo.apollo.repo.converters.enums.MediaTypeConverter.toDomain
import daxo.core.model.media.media.full.FullMediaCard
import daxo.core.model.byApollo.media.BasicCharacterQuery
import daxo.the.apollo.FullMediaCardQuery

object ApolloResponseConverter {
    fun toDomain(input: FullMediaCardQuery.Media?): FullMediaCard? {
        if (input == null) return null
        return FullMediaCard(
            id = input.id,
            mediaType = input.type?.toDomain(),
            mediaFormat = input.format?.toDomain(),
            mediaStatus = input.status?.toDomain(),
            description = input.description,
            mediaSeason = input.season?.toDomain(),
            seasonYear = input.seasonYear,
            episodes = input.episodes,
            duration = input.duration,
            chapters = input.chapters,
            volumes = input.volumes,
            countryOfOrigin = input.countryOfOrigin as? String,
            isLicensed = input.isLicensed,
            source = input.source?.toDomain(),
            hashtag = input.hashtag,
            updatedAt = input.updatedAt,
            bannerImage = input.bannerImage,
            genres = input.genres?.filterNotNull(),
            synonyms = input.synonyms?.filterNotNull(),
            averageScore = input.averageScore,
            meanScore = input.meanScore,
            popularity = input.popularity,
            isLocked = input.isLocked,
            trending = input.trending,
            favourites = input.favourites,
            isFavourite = input.isFavourite,
            isFavouriteBlocked = input.isFavouriteBlocked,
            siteUrl = input.siteUrl,
            autoCreateForumThread = input.autoCreateForumThread,
            isRecommendationBlocked = input.isRecommendationBlocked,
            isReviewBlocked = input.isReviewBlocked,
            modNotes = input.modNotes,
            isAdult = input.isAdult,
            title = input.title?.mediaTitle?.toDomain(),
            startDate = input.startDate?.fuzzyDate?.toDomain(),
            endDate = input.endDate?.fuzzyDate?.toDomain(),
            trailer = input.trailer?.mediaTrailer?.toDomain(),
            fullCoverImage = input.coverImage?.mediaCoverImage?.toDomain(),
            tags = input.tags?.mapNotNull { it?.mediaTag?.toDomain() },
            relations = input.relations?.basicMediaRelationQuery?.toDomain(),
            characters = input.characters?.edges?.map {
                BasicCharacterQuery(
                    it?.role?.toDomain(),
                    it?.node?.basicCharacter?.toDomain(),
                    it?.voiceActors?.mapNotNull { it?.basicStaff?.toDomain() })
            },
            staff = input.staff?.basicStaffQuery?.edges?.mapNotNull { it?.node?.basicStaff?.toDomain() },
            studios = input.studios?.basicStudioQuery?.toDomain()?.studios,
            nextAiringEpisode = input.nextAiringEpisode?.nextAiringEpisode?.toDomain(),
            externalMediaLinks = input.externalLinks?.filterNotNull()?.map { it.mediaExternalLink.toDomain() },
            recommendations = input.recommendations?.basicRecommendationsQuery?.edges?.mapNotNull { it?.node?.media?.basicMedia?.toDomain() },
            reviews = input.reviews?.basicReviewQuery?.edges?.mapNotNull { it?.node?.basicReview?.toDomain() },
            stats = input.stats?.basicStats?.toDomain(),
            airingSchedule = input.airingSchedule?.edges?.mapNotNull { it?.node?.nextAiringEpisode?.toDomain() },
            mediaStreamingEpisodes = input.streamingEpisodes?.mapNotNull { it?.mediaStreamingEpisode?.toDomain() },
            rankings = input.rankings?.mapNotNull { it?.mediaRanking?.toDomain() }
        )
    }
}