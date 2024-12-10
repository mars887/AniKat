package daxo.apollo.repo.converters.enums

import daxo.the.apollo.type.MediaSource as ApolloModel
import daxo.core.model.media.enums.MediaSource as DomainModel

object MediaSourceConverter {
    fun ApolloModel.toDomain(): DomainModel {
        return DomainModel.safeValueOf(rawValue)
    }

    fun DomainModel.toApollo(): ApolloModel {
        return ApolloModel.safeValueOf(rawValue)
    }
}