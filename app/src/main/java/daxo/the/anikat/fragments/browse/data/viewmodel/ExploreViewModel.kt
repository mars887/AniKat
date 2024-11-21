package daxo.the.anikat.fragments.browse.data.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import daxo.core.model.media.enums.MediaType
import daxo.the.anikat.fragments.browse.data.entity.ExtendedMediaCardListScrollable
import daxo.the.anikat.fragments.browse.data.entity.toScrollable
import daxo.the.anikat.fragments.browse.usecases.LoadMediaCardListsUseCase
import daxo.the.anikat.fragments.browse.usecases.PaginateMediaLineUseCase
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

    private val data: MutableSharedFlow<List<ExtendedMediaCardListScrollable>> = MutableSharedFlow(replay = 1)

    fun getData(): SharedFlow<List<ExtendedMediaCardListScrollable>> = data.asSharedFlow()

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

    fun paginateMediaList(dataToPaginate: ExtendedMediaCardListScrollable) {
        if(System.currentTimeMillis() - last < 500) return

        last = System.currentTimeMillis()
        viewModelScope.launch {
            val loadedData = paginateMediaLineUseCase(dataToPaginate.extendedMediaCardList)
                ?.toScrollable(dataToPaginate.scrollPosition)

            if (loadedData == null) return@launch

            val currentData = data.replayCache[0]
            val newList = mutableListOf<ExtendedMediaCardListScrollable>()
            currentData.forEach {
                newList += if (dataToPaginate.extendedMediaCardList.listName == it.extendedMediaCardList.listName) loadedData else it
            }

            data.emit(newList)
        }
    }

    companion object {
        private const val TAG = "ExploreViewModel"
    }
}