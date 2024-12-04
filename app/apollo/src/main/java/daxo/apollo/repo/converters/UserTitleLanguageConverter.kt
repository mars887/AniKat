package daxo.apollo.repo.converters

import daxo.core.model.media.enums.UserTitleLanguage as DomainModel
import daxo.the.apollo.type.UserTitleLanguage as ApolloModel

object UserTitleLanguageConverter {
    fun ApolloModel.toDomain(): DomainModel {
        return DomainModel.safeValueOf(rawValue)
    }

    fun DomainModel.toApollo(): ApolloModel {
        return ApolloModel.safeValueOf(rawValue)
    }
}