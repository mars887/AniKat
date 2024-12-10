package daxo.apollo.repo.converters.enums

import daxo.core.model.media.enums.MediaRankType as DomainModel
import daxo.the.apollo.type.MediaRankType as ApolloModel


object MediaRankConverter {
    fun ApolloModel.toDomain(): DomainModel {
        return DomainModel.safeValueOf(rawValue)
    }

    fun DomainModel.toApollo(): ApolloModel {
        return ApolloModel.safeValueOf(rawValue)
    }
}