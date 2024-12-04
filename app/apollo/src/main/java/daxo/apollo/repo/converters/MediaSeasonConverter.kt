package daxo.apollo.repo.converters

import daxo.the.apollo.type.MediaSeason as ApolloMediaSeason
import daxo.core.model.media.enums.MediaSeason as DomainMediaSeason

object MediaSeasonConverter {
    fun ApolloMediaSeason.toDomain(): DomainMediaSeason {
        return DomainMediaSeason.valueOf(rawValue)
    }

    fun DomainMediaSeason.toApollo(): ApolloMediaSeason {
        return ApolloMediaSeason.valueOf(this.rawValue)
    }
}