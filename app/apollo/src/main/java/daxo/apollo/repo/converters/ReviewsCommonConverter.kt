package daxo.apollo.repo.converters

import daxo.apollo.repo.converters.UserCommonConverter.toDomian
import daxo.the.apollo.fragment.BasicReview as ApolloBasicReview
import daxo.core.model.byApollo.media.BasicReview as DomainBasicReview
import daxo.the.apollo.fragment.BasicReviewQuery as ApolloBasicReviewQuery
import daxo.core.model.byApollo.media.BasicReviewQuery as DomainBasicReviewQuery

object ReviewsCommonConverter {
    fun ApolloBasicReviewQuery.toDomain(): DomainBasicReviewQuery? {
        if (edges == null) return null
        return DomainBasicReviewQuery(edges.mapNotNull {
            it?.node?.basicReview?.toDomain()
        })
    }

    fun ApolloBasicReview.toDomain(): DomainBasicReview {
        return DomainBasicReview(id, userId, summary, score, user?.basicUser?.toDomian())
    }
}