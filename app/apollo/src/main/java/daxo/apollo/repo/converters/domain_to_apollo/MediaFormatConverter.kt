package daxo.apollo.repo.converters.domain_to_apollo

import daxo.the.apollo.type.MediaFormat as ApolloMediaFormat
import daxo.the.domain.model.media.enums.MediaFormat as DomainMediaFormat

object MediaFormatConverter {
    fun toDomain(mediaType: ApolloMediaFormat): DomainMediaFormat {
        return DomainMediaFormat.valueOf(mediaType.rawValue)
    }

    fun convertToApollo(mediaType: DomainMediaFormat): ApolloMediaFormat {
        return ApolloMediaFormat.valueOf(mediaType.rawValue)
    }

    fun DomainMediaFormat.toApollo(): ApolloMediaFormat {
        return ApolloMediaFormat.valueOf(this.rawValue)
    }
}