package daxo.the.data.api.explore

import com.apollographql.apollo.ApolloClient
import daxo.the.data.api.ApiRequestsController

class ExploreMediaStorage(
    private val apolloClient: ApolloClient
) {
    suspend fun loadContent(
        params: ExploreQueryParams
    ) {
        //val query = FilteredContentPageQuery
    }
}