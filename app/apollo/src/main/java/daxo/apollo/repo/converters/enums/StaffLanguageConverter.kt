package daxo.apollo.repo.converters.enums

import daxo.the.apollo.type.StaffLanguage as ApolloModel
import daxo.core.model.media.enums.StaffLanguage as DomainModel

object StaffLanguageConverter {
    fun ApolloModel.toDomain(): DomainModel {
        return DomainModel.safeValueOf(rawValue)
    }

    fun DomainModel.toApollo(): ApolloModel {
        return ApolloModel.safeValueOf(rawValue)
    }
}