package daxo.the.anikat.core.repo

import android.util.Log
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import daxo.the.anikat.FilteredContentPageQuery
import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

class MediaRepo @Inject constructor(
    private val pagesController: PagesController,
    private val apolloClient: ApolloClient
) {
    private var lastRequest: Long = 0
    private val timeoutCheckMutex = Mutex(false)

    suspend fun loadContentBy(
        pageConfig: PageConfig,
        params: QueryParams
    ): List<FilteredContentPageQuery.Medium?>? {
        timeoutCheckMutex.withLock {
            while(checkTimeout()) {
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
        val response = try {
            apolloClient.query(query).execute()
        } catch (e: Exception) {
            Log.e("ApolloClient", "Query execution failed", e)
            return null
        }

        if (response.hasErrors()) {
            response.errors?.forEach {
                Log.e("ApolloClient", "GraphQL Error: ${it.message}")
            }
            return null
        }
        Log.d("ApolloClient", "loading completed")
        return response.data?.Page?.media
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