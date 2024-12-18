package daxo.apollo.repo.extended_media

import daxo.apollo.repo.converters.AiringScheduleCommonConverter.toDomain
import daxo.apollo.repo.converters.MediaCommonConverter.toDomain
import daxo.apollo.repo.converters.StudiosCommonConverter.toDomain
import daxo.apollo.repo.converters.enums.MediaFormatConverter.toDomain
import daxo.apollo.repo.converters.enums.MediaSeasonConverter.toDomain
import daxo.apollo.repo.converters.enums.MediaTypeConverter.toDomain
import daxo.the.apollo.GetMediaExtendedCardFilteredQuery
import daxo.core.model.media.media.extended.ExtendedMediaCard
import java.util.Date

object ApolloResponseConverter {

    fun toDomain(media: List<GetMediaExtendedCardFilteredQuery.Medium?>?): List<ExtendedMediaCard> {
        val cards = mutableListOf<ExtendedMediaCard>()
        media?.filterNotNull()?.forEach {
            cards += ExtendedMediaCard(
                mediaId = it.id,
                mediaType = it.type?.toDomain(),
                title = it.title?.mediaTitle?.toDomain(),
                studios = it.studios?.basicStudioQuery?.toDomain(),
                season = it.season?.toDomain(),
                seasonYear = it.seasonYear,
                nextAiringEpisode = it.nextAiringEpisode?.nextAiringEpisode?.toDomain(),
                format = it.format?.toDomain(),
                description = it.description,
                episodes = it.episodes,
                genres = it.genres?.filterNotNull(),
                averageScore = it.averageScore,
                favourites = it.favourites,
                popularity = it.popularity,
                coverImage = it.coverImage?.mediaCoverImage?.toDomain(),
                bannerImage = it.bannerImage,
                lastUpdate = Date()
            )
        }

        return cards
    }
}