package daxo.apollo.repo.converters.enums

import daxo.core.model.media.enums.MediaRelationType as DomainModel
import daxo.the.apollo.type.MediaRelation as ApolloModel

object MediaRelationTypeConverter {
    fun ApolloModel.toDomain(): DomainModel {
        return DomainModel.safeValueOf(rawValue)
    }

    fun DomainModel.toApollo(): ApolloModel {
        return ApolloModel.safeValueOf(rawValue)
    }
}