package daxo.apollo.repo.converters.enums

import daxo.the.apollo.type.ExternalLinkType as ApolloModel
import daxo.core.model.media.enums.ExternalLinkType as DomainModel

object ExternalLinkTypeConverter {
    fun ApolloModel.toDomain(): DomainModel {
        return DomainModel.safeValueOf(rawValue)
    }

    fun DomainModel.toApollo(): ApolloModel {
        return ApolloModel.safeValueOf(rawValue)
    }
}