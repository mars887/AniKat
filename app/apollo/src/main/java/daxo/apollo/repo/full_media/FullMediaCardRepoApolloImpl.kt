package daxo.apollo.repo.full_media

import android.util.Log
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import daxo.apollo.QueryTimeController
import daxo.apollo.repo.converters.enums.MediaTypeConverter.toApollo
import daxo.apollo.repo.converters.enums.StaffLanguageConverter.toApollo
import daxo.core.model.media.enums.MediaType
import daxo.core.model.media.media.full.FullMediaCard
import daxo.the.apollo.FullMediaCardQuery
import daxo.the.data.interfaces.media_get.FMCRequestParams
import daxo.the.data.interfaces.media_get.IFullMediaCardRepo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class FullMediaCardRepoApolloImpl @Inject constructor(
    private val apolloClient: ApolloClient,
    private val queryTimeController: QueryTimeController,
) : IFullMediaCardRepo {

    override suspend fun getFullMediaCard(id: Int, type: MediaType, params: FMCRequestParams): FullMediaCard? {
        while (queryTimeController.checkTime()) delay(50)

        val query = FullMediaCardQuery(
            id = Optional.presentIfNotNull(id),
            type = Optional.presentIfNotNull(type.toApollo()),
            innerListsPerPage = Optional.presentIfNotNull(params.innerListsPerPage),
            innerListsPage = Optional.presentIfNotNull(params.innerListsPage),
            voiceActorsLanguage = Optional.presentIfNotNull(params.voiceActorsLanguage.toApollo())
        )

        val result = apolloClient.query(query)
            .failFastIfOffline(true)
            .execute()

        result.errors?.let { it ->
            it.forEach {
                Log.i(TAG, " errors: ${it.message}")
            }
        }
        result.exception?.let {
            Log.i(TAG, " exception: ${it.message}")
        }

        return ApolloResponseConverter.toDomain(result.data?.Media)
    }

    override suspend fun getFullMediaCardFlow(id: Int, type: MediaType, params: FMCRequestParams): Flow<FullMediaCard> {
        while (queryTimeController.checkTime()) delay(50)

        val query = FullMediaCardQuery(
            id = Optional.presentIfNotNull(id),
            type = Optional.presentIfNotNull(type.toApollo()),
            innerListsPerPage = Optional.presentIfNotNull(params.innerListsPerPage),
            innerListsPage = Optional.presentIfNotNull(params.innerListsPage),
            voiceActorsLanguage = Optional.presentIfNotNull(params.voiceActorsLanguage.toApollo())
        )

        val result = apolloClient.query(query)
            .failFastIfOffline(true)
            .toFlow()

        return result.onEach { result ->
            result.errors?.let { it ->
                it.forEach {
                    Log.i(TAG, " errors: ${it.message}")
                }
            }
            result.exception?.let {
                Log.i(TAG, " exception: ${it.message}")
            }
        }.map { result ->
            ApolloResponseConverter.toDomain(result.data?.Media)
        }.filterNotNull()
    }

    companion object {
        private const val TAG = "FullMediaCardRepoApollo"
    }
}