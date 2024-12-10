package daxo.apollo.repo.converters.enums

import daxo.the.apollo.type.MediaListStatus as ApolloModel
import daxo.core.model.media.enums.MediaListStatus as DomainModel

object MediaListStatusConverter {
    fun ApolloModel.toDomain(): DomainModel {
        return DomainModel.safeValueOf(rawValue)
    }

    fun DomainModel.toApollo(): ApolloModel {
        return ApolloModel.safeValueOf(rawValue)
    }
}