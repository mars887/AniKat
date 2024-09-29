package daxo.the.anikat.core.repo

import android.util.Log
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.apollographql.apollo.api.Query
import com.apollographql.apollo.cache.normalized.FetchPolicy
import com.apollographql.apollo.cache.normalized.fetchPolicy
import com.apollographql.apollo.cache.normalized.isFromCache
import com.apollographql.apollo.cache.normalized.watch
import daxo.the.anikat.FilteredContentPageQuery
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

class MediaRepo @Inject constructor(
    private val pagesController: PagesController,
    private val apolloClient: ApolloClient
) {
    private var lastRequest: Long = 0
    private val timeoutCheckMutex = Mutex(false)

    private val queryCache = mutableMapOf<Query<*>, Long>()
    private val cachingTimeout = 15000 // in ms

    suspend fun loadContentBy(
        pageConfig: PageConfig,
        params: QueryParams
    ): Flow<Pair<List<FilteredContentPageQuery.Medium?>, Boolean>> {
        timeoutCheckMutex.withLock {
            while (checkTimeout()) {
                delay(100)
            }
        }
        Log.d("ApolloClient", "loading started")

        val query = FilteredContentPageQuery(
            Optional.presentIfNotNull(pagesController.getPageIdFor(pageConfig)),
            Optional.presentIfNotNull(pageConfig.perPage),
            Optional.presentIfNotNull(params.type),
            Optional.presentIfNotNull(params.sort),
            Optional.presentIfNotNull(params.season),
            Optional.presentIfNotNull(params.seasonYear),
            Optional.presentIfNotNull(params.genre),
            Optional.presentIfNotNull(params.format),
            Optional.presentIfNotNull(params.search),
            Optional.presentIfNotNull(params.isAdult)
        )
        val timer = queryCache[query]
        val fetchPolicy =
            if (timer == null || System.currentTimeMillis() - timer > cachingTimeout) { // TODO test
                queryCache[query] = System.currentTimeMillis()
                FetchPolicy.CacheAndNetwork
            } else {
                FetchPolicy.CacheFirst
            }

        return apolloClient.query(query)
            .fetchPolicy(fetchPolicy)
            .failFastIfOffline(true)
            .toFlow()
            .filter {
                it.data?.Page?.media != null
            }.map {
                Pair(it.data?.Page?.media!!, it.isFromCache)
            }.filterNotNull()
    }

    private fun checkTimeout(): Boolean =
        if (System.currentTimeMillis() - lastRequest > REQUEST_TIMEOUT) {
            lastRequest = System.currentTimeMillis()
            false
        } else true


    private companion object {
        const val REQUEST_TIMEOUT: Int = 900
    }
}