package daxo.apollo.repo.converters.enums

import daxo.the.apollo.type.MediaStatus as ApolloModel
import daxo.core.model.media.enums.MediaStatus as DomainModel

object MediaStatusConverter {
    fun ApolloModel.toDomain(): DomainModel {
        return DomainModel.safeValueOf(rawValue)
    }

    fun DomainModel.toApollo(): ApolloModel {
        return ApolloModel.safeValueOf(rawValue)
    }
}