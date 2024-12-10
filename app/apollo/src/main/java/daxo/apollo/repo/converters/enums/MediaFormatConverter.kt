package daxo.apollo.repo.converters.enums

import daxo.the.apollo.type.MediaFormat as ApolloModel
import daxo.core.model.media.enums.MediaFormat as DomainModel

object MediaFormatConverter {
    fun ApolloModel.toDomain(): DomainModel {
        return DomainModel.safeValueOf(rawValue)
    }

    fun DomainModel.toApollo(): ApolloModel {
        return ApolloModel.safeValueOf(rawValue)
    }
}