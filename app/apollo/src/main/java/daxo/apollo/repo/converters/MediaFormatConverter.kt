package daxo.apollo.repo.converters

import daxo.the.apollo.type.MediaFormat as ApolloMediaFormat
import daxo.core.model.media.enums.MediaFormat as DomainMediaFormat

object MediaFormatConverter {
    fun ApolloMediaFormat.toDomain(): DomainMediaFormat {
        return DomainMediaFormat.valueOf(rawValue)
    }

    fun DomainMediaFormat.toApollo(): ApolloMediaFormat {
        return ApolloMediaFormat.valueOf(this.rawValue)
    }
}