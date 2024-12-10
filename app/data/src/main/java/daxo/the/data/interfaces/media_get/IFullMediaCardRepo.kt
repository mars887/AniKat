package daxo.the.data.interfaces.media_get

import daxo.core.model.media.enums.MediaType
import daxo.core.model.media.media.full.FullMediaCard
import kotlinx.coroutines.flow.Flow

interface IFullMediaCardRepo {
    suspend fun getFullMediaCard(id: Int, type: MediaType,params: FMCRequestParams): FullMediaCard?
    suspend fun getFullMediaCardFlow(id: Int, type: MediaType,params: FMCRequestParams): Flow<FullMediaCard>
}