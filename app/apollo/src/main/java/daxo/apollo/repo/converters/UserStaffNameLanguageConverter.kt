package daxo.apollo.repo.converters

import daxo.core.model.media.enums.UserStaffNameLanguage as DomainModel
import daxo.the.apollo.type.UserStaffNameLanguage as ApolloModel

object UserStaffNameLanguageConverter {
    fun ApolloModel.toDomain(): DomainModel {
        return DomainModel.safeValueOf(rawValue)
    }

    fun DomainModel.toApollo(): ApolloModel {
        return ApolloModel.safeValueOf(rawValue)
    }
}