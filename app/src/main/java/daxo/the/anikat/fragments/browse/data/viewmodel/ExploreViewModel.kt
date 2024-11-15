package daxo.the.anikat.fragments.browse.data.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import daxo.services.BasicMediaPageService
import daxo.services.BasicMediaPageService.Companion
import daxo.the.anikat.fragments.browse.data.entity.BasicMediaCardListScrollable
import daxo.the.anikat.fragments.browse.data.entity.toScrollable
import daxo.the.anikat.fragments.browse.usecases.LoadMediaCardListsUseCase
import daxo.the.anikat.fragments.browse.usecases.PaginateMediaLineUseCase
import daxo.the.domain.model.media.enums.MediaType
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val paginateMediaLineUseCase: PaginateMediaLineUseCase,
    private val loadMediaCardLists: LoadMediaCardListsUseCase
) : ViewModel() {

    private val data: MutableSharedFlow<List<BasicMediaCardListScrollable>> = MutableSharedFlow(replay = 1)

    fun getData(): SharedFlow<List<BasicMediaCardListScrollable>> = data.asSharedFlow()

    var mediaType: MediaType? = null
        set(value) {
            field = value
            loadInitData()
        }

    private fun loadInitData() {
        Log.i(TAG, "loadInitData: start")
        viewModelScope.launch {
            val lists = loadMediaCardLists(mediaType!!).map {
                it.toScrollable()
            }
            data.emit(lists)
        }
    }

    private var last : Long = 0 // todo

    fun paginateMediaList(dataToPaginate: BasicMediaCardListScrollable) {
        if(System.currentTimeMillis() - last < 500) return

        last = System.currentTimeMillis()
        viewModelScope.launch {
            val loadedData = paginateMediaLineUseCase(dataToPaginate.basicMediaCardList)
                ?.toScrollable(dataToPaginate.scrollPosition)

            if (loadedData == null) return@launch

            val currentData = data.replayCache[0]
            val newList = mutableListOf<BasicMediaCardListScrollable>()
            currentData.forEach {
                newList += if (dataToPaginate.basicMediaCardList.listName == it.basicMediaCardList.listName) loadedData else it
            }

            data.emit(newList)
        }
    }

    companion object {
        private const val TAG = "ExploreViewModel"
    }
}