package daxo.apollo.repo.converters.enums

import daxo.the.apollo.type.MediaType as ApolloModel
import daxo.core.model.media.enums.MediaType as DomainModel

object MediaTypeConverter {
    fun ApolloModel.toDomain(): DomainModel {
        return DomainModel.safeValueOf(rawValue)
    }

    fun DomainModel.toApollo(): ApolloModel {
        return ApolloModel.safeValueOf(rawValue)
    }
}