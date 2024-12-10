package daxo.apollo.repo.converters.enums

import daxo.the.apollo.type.MediaSort as ApolloModel
import daxo.core.model.media.enums.MediaSort as DomainModel

object MediaSortConverter {
    fun ApolloModel.toDomain(): DomainModel {
        return DomainModel.safeValueOf(rawValue)
    }

    fun DomainModel.toApollo(): ApolloModel {
        return ApolloModel.safeValueOf(rawValue)
    }
}