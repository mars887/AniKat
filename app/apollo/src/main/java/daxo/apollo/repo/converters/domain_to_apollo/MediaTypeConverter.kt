package daxo.apollo.repo.converters.domain_to_apollo

import daxo.the.apollo.type.MediaType as ApolloMediaType
import daxo.the.domain.model.media.enums.MediaType as DomainMediaType

object MediaTypeConverter {
    fun toDomain(mediaType: ApolloMediaType): DomainMediaType {
        return DomainMediaType.valueOf(mediaType.rawValue)
    }

    fun convertToApollo(mediaType: DomainMediaType): ApolloMediaType {
        return ApolloMediaType.valueOf(mediaType.rawValue)
    }

    fun DomainMediaType.toApollo(): ApolloMediaType {
        return ApolloMediaType.valueOf(this.rawValue)
    }
}