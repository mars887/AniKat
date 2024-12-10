package daxo.apollo.repo.converters

import daxo.the.apollo.fragment.BasicCharacterName as ApolloBasicCharacterName
import daxo.core.model.byApollo.media.BasicCharacterName as DomainBasicCharacterName
import daxo.the.apollo.fragment.CharacterImage as ApolloCharacterImage
import daxo.core.model.byApollo.media.CharacterImage as DomainCharacterImage
import daxo.the.apollo.fragment.BasicCharacter as ApolloBasicCharacter
import daxo.core.model.byApollo.media.BasicCharacter as DomainBasicCharacter

object CharacterCommonConverter {
    fun ApolloBasicCharacter.toDomain(): DomainBasicCharacter {
        return DomainBasicCharacter(id, name?.basicCharacterName?.toDomain(), image?.characterImage?.toDomain(), age)
    }

    fun ApolloCharacterImage.toDomain(): DomainCharacterImage {
        return DomainCharacterImage(large, medium)
    }

    fun ApolloBasicCharacterName.toDomain(): DomainBasicCharacterName {
        return DomainBasicCharacterName(first, last, full)
    }
}