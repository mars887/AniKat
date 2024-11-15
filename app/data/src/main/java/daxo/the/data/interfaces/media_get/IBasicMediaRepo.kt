package daxo.the.data.interfaces.media_get

import daxo.the.data.api.explore.LoadMediaPagesParams
import daxo.the.domain.model.media.BasicMediaCard
import kotlinx.coroutines.flow.Flow

interface IBasicMediaRepo {
    suspend fun loadPages(params: LoadMediaPagesParams): List<BasicMediaCard>
}