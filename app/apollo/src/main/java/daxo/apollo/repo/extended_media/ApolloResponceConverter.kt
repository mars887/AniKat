package daxo.apollo.repo.extended_media

import daxo.apollo.repo.converters.MediaFormatConverter.toDomain
import daxo.apollo.repo.converters.MediaSeasonConverter.toDomain
import daxo.apollo.repo.extended_media.converters.*
import daxo.the.apollo.GetMediaExtendedCardFilteredQuery
import daxo.core.model.media.ExtendedMediaCard
import java.util.Date

object ApolloResponceConverter {

    fun toDomain(media: List<GetMediaExtendedCardFilteredQuery.Medium?>?): List<ExtendedMediaCard> {
        val cards = mutableListOf<ExtendedMediaCard>()
        media?.filterNotNull()?.forEach {
            cards += ExtendedMediaCard(
                mediaId = it.id,
                title = it.title?.toDomain(),
                studios = it.studios?.toDomain(),
                season = it.season?.toDomain(),
                seasonYear = it.seasonYear,
                nextAiringEpisode = it.nextAiringEpisode?.toDomain(),
                format = it.format?.toDomain(),
                description = it.description,
                episodes = it.episodes,
                genres = it.genres?.filterNotNull(),
                averageScore = it.averageScore,
                favourites = it.favourites,
                coverImage = it.coverImage?.toDomain(),
                bannerImage = it.bannerImage,
                lastUpdate = Date()
            )
        }

        return cards
    }
}