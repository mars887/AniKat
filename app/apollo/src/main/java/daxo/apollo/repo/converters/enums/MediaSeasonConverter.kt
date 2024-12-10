package daxo.apollo.repo.converters.enums

import daxo.the.apollo.type.MediaSeason as ApolloModel
import daxo.core.model.media.enums.MediaSeason as DomainModel

object MediaSeasonConverter {
    fun ApolloModel.toDomain(): DomainModel {
        return DomainModel.safeValueOf(rawValue)
    }

    fun DomainModel.toApollo(): ApolloModel {
        return ApolloModel.safeValueOf(rawValue)
    }
}