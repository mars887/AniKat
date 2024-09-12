package daxo.the.anikat.fragments.browse.data.viewmodel

import androidx.lifecycle.ViewModel
import daxo.the.anikat.fragments.browse.data.entity.MediaLineData
import daxo.the.anikat.fragments.browse.usecases.GetMediaBoardsUseCase
import daxo.the.anikat.type.MediaType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ExploreViewModel @Inject constructor(
    private val getMediaBoardsUseCase: GetMediaBoardsUseCase
) : ViewModel() {
    private val data: MutableSharedFlow<List<MediaLineData>> = MutableSharedFlow(replay = 1)

    fun getData(): SharedFlow<List<MediaLineData>> = data.asSharedFlow()


    suspend fun reloadDataFlow(mediaType: MediaType) {
        println("loading data in EVM")
        data.emitAll(getMediaBoardsUseCase(mediaType))
    }

    suspend fun paginateLine(mediaType: MediaType, lineData: MediaLineData): Flow<MediaLineData> {
        println("paginating line ${lineData.lineName}")
        return getMediaBoardsUseCase.paginateByTag(mediaType, lineData.tag)
    }
}