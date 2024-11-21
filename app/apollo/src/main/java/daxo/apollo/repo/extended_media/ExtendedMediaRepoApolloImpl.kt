package daxo.apollo.repo.extended_media

import android.util.Log
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import daxo.apollo.QueryTimeController
import daxo.apollo.repo.converters.MediaFormatConverter.toApollo
import daxo.apollo.repo.converters.MediaSeasonConverter.toApollo
import daxo.apollo.repo.converters.MediaSortConverter.toApollo
import daxo.apollo.repo.converters.MediaTypeConverter.toApollo
import daxo.the.apollo.GetMediaExtendedCardFilteredQuery
import daxo.the.data.api.explore.LoadMediaPagesParams
import daxo.the.data.interfaces.media_get.IExtendedMediaRepo
import daxo.core.model.media.ExtendedMediaCard
import kotlinx.coroutines.delay
import javax.inject.Inject

class ExtendedMediaRepoApolloImpl @Inject constructor(
    private val apolloClient: ApolloClient,
    private val queryTimeController: QueryTimeController,
):IExtendedMediaRepo {
    override suspend fun loadPages(params: LoadMediaPagesParams): List<ExtendedMediaCard>? {
        while(queryTimeController.checkTime()) delay(50)

        val query = GetMediaExtendedCardFilteredQuery(
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

        return ApolloResponceConverter.toDomain(
            result.data?.Page?.media
        )
    }

    override suspend fun loadById(id: Int): ExtendedMediaCard? {
        TODO("Not yet implemented")
    }

    companion object {
        private const val TAG = "ExtendedMediaRepoApollo"
    }
}