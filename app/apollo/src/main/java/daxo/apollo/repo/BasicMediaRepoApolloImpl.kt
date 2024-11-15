package daxo.apollo.repo

import android.util.Log
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import daxo.apollo.repo.converters.ApolloResponseConverter
import daxo.apollo.repo.converters.domain_to_apollo.MediaFormatConverter.toApollo
import daxo.apollo.repo.converters.domain_to_apollo.MediaSeasonConverter.toApollo
import daxo.apollo.repo.converters.domain_to_apollo.MediaSortConverter.toApollo
import daxo.apollo.repo.converters.domain_to_apollo.MediaTypeConverter.toApollo
import daxo.the.apollo.FilteredContentPageQuery
import daxo.the.data.api.explore.LoadMediaPagesParams
import daxo.the.data.interfaces.media_get.IBasicMediaRepo
import daxo.the.domain.model.media.BasicMediaCard
import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject


class BasicMediaRepoApolloImpl @Inject constructor(
    private val apolloClient: ApolloClient,
) : IBasicMediaRepo {

    private val lastQuery: Long = 0
    private val timerMutex = Mutex()

    override suspend fun loadPages(params: LoadMediaPagesParams): List<BasicMediaCard> {
        while (checkTime()) {
            delay(50)
        }
        val query = FilteredContentPageQuery(
            page = Optional.presentIfNotNull(params.page),
            perPage = Optional.presentIfNotNull(params.perPage),
            type = Optional.presentIfNotNull(params.type.toApollo()),
            sort = Optional.presentIfNotNull(params.sort.map { it.toApollo() }),
            season = Optional.presentIfNotNull(params.season?.toApollo()),
            seasonYear = Optional.presentIfNotNull(params.seasonYear),
            genre = Optional.presentIfNotNull(params.genre.takeIf { it?.isNotBlank() ?: false }),
            format = Optional.presentIfNotNull(params.format?.toApollo()),
            search = Optional.presentIfNotNull(params.search.takeIf { it?.isNotBlank() ?: false }),
            isAdult = Optional.presentIfNotNull(params.isAdult)
        )

        Log.i(TAG, "loadPages params: $params")
        Log.i(TAG, "loadPages query: $query")

        val result = apolloClient.query(query)
            .failFastIfOffline(true)
            .execute()

        result.errors?.let { it ->
            it.forEach {
                Log.i(TAG, "loadPages errors: ${it.message}")
            }
        }
        result.exception?.let {
            Log.i(TAG, "loadPages exception: ${it.message}")
        }

        return ApolloResponseConverter.toDomain(
            result.data?.Page?.media
        )
    }

    private suspend fun checkTime(): Boolean {
        timerMutex.withLock {
            return System.currentTimeMillis() - lastQuery < REQUEST_DELAY
        }
    }

    private companion object {
        private const val REQUEST_DELAY = 700
        private const val TAG = "BasicMediaRepoApolloImp"
    }
}