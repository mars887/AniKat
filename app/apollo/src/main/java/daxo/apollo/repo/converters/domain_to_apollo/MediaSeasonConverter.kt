package daxo.apollo.repo.converters.domain_to_apollo

import daxo.the.apollo.type.MediaSeason as ApolloMediaSeason
import daxo.the.domain.model.media.enums.MediaSeason as DomainMediaSeason

object MediaSeasonConverter {
    fun toDomain(mediaType: ApolloMediaSeason): DomainMediaSeason {
        return DomainMediaSeason.valueOf(mediaType.rawValue)
    }

    fun convertToApollo(mediaType: DomainMediaSeason): ApolloMediaSeason {
        return ApolloMediaSeason.valueOf(mediaType.rawValue)
    }

    fun DomainMediaSeason.toApollo(): ApolloMediaSeason {
        return ApolloMediaSeason.valueOf(this.rawValue)
    }
}