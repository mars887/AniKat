package daxo.apollo.repo.converters

import daxo.core.model.byApollo.media.BasicUser as DomainBasicUser
import daxo.core.model.byApollo.media.UserAvatar as DomainUserAvatar
import daxo.the.apollo.fragment.BasicUser as ApolloBasicUser
import daxo.the.apollo.fragment.UserAvatar as ApolloUserAvatar

object UserCommonConverter {

    fun ApolloBasicUser.toDomian(): DomainBasicUser {
        return DomainBasicUser(id,name,avatar?.userAvatar?.toDomain())
    }

    fun ApolloUserAvatar.toDomain(): DomainUserAvatar {
        return DomainUserAvatar(large, medium)
    }
}