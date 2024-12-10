package daxo.apollo.repo.converters

import daxo.apollo.repo.converters.MediaCommonConverter.toDomain
import daxo.the.apollo.fragment.NextAiringEpisode as ApolloNextAiringEpisode
import daxo.core.model.byApollo.media.NextAiringEpisode as DomainNextAiringEpisode

object AiringScheduleCommonConverter {
    fun ApolloNextAiringEpisode.toDomain(): DomainNextAiringEpisode {
        return DomainNextAiringEpisode(id, airingAt, timeUntilAiring, episode, mediaId, media?.basicMedia?.toDomain())
    }
}