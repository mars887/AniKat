package daxo.apollo.repo.converters

import daxo.apollo.repo.converters.MediaCommonConverter.toDomain
import daxo.the.apollo.fragment.BasicRecommendationsQuery as ApolloBasicRecommendationsQuery
import daxo.core.model.byApollo.media.BasicRecommendationsQuery as DomainBasicRecommendationsQuery


object RecommendationsCommonConverter {
    fun ApolloBasicRecommendationsQuery.toDomain(): DomainBasicRecommendationsQuery? {
        if (edges == null) return null
        return DomainBasicRecommendationsQuery(
            edges.mapNotNull { it?.node?.media?.basicMedia?.toDomain() }
        )
    }
}