package daxo.apollo.repo.converters

import daxo.the.apollo.type.MediaType as ApolloMediaType
import daxo.core.model.media.enums.MediaType as DomainMediaType

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