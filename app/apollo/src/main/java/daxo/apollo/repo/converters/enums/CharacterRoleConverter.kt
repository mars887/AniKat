package daxo.apollo.repo.converters.enums

import daxo.core.model.media.enums.CharacterRole
import daxo.core.model.media.enums.CharacterRole as DomainModel
import daxo.the.apollo.type.CharacterRole as ApolloModel

object CharacterRoleConverter {
    fun ApolloModel.toDomain(): DomainModel {
        return CharacterRole.safeValueOf(rawValue)
    }

    fun DomainModel.toApollo(): ApolloModel {
        return ApolloModel.safeValueOf(rawValue)
    }
}