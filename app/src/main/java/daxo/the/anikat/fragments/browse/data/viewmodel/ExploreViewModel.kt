package daxo.the.anikat.fragments.browse.data.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import daxo.the.anikat.fragments.browse.data.entity.MediaLineData
import daxo.the.anikat.fragments.browse.usecases.GetMediaBoardsUseCase
import daxo.the.anikat.type.MediaType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.EmptyCoroutineContext

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val getMediaBoardsUseCase: GetMediaBoardsUseCase
) : ViewModel() {
    private val data: MutableSharedFlow<List<MediaLineData>> = MutableSharedFlow(replay = 1)

    init {
        println("!!! evm created")
        CoroutineScope(EmptyCoroutineContext).launch {
            data.collect {
                //println(it.map { "${it.tag}-${it.unic} " }.joinToString(separator = " "))
            }
        }
    }

    fun getData(): SharedFlow<List<MediaLineData>> = data.asSharedFlow()

    suspend fun reloadDataFlow(mediaType: MediaType, check: Boolean = true) {
        if (check && data.replayCache.isEmpty()) {
            data.emitAll(getMediaBoardsUseCase(mediaType))
            //println("loading data in EVM")
        } else {
            //println("no loading data in EVM")
        }
    }

    suspend fun paginateLine(
        mediaType: MediaType,
        lineData: MediaLineData,
        func: suspend (MediaLineData,Boolean) -> Unit
    ) {
        //println("paginating line ${lineData.lineName}")
        getMediaBoardsUseCase.paginateByTag(mediaType, lineData, func)
    }
}