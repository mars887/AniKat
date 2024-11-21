package daxo.the.anikat.fragments.mediaviewinghistory

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import daxo.core.model.media.ExtendedMediaCardViewed
import daxo.the.anikat.fragments.mediaviewinghistory.test.DataLoader
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaHistoryViewModel @Inject constructor(
    private val loader: DataLoader
) : ViewModel() {

    private val _data: MutableSharedFlow<List<ExtendedMediaCardViewed>> = MutableSharedFlow(replay = 1)
    val data get() = _data.asSharedFlow()

    init {
        viewModelScope.launch {
            loader.state.collect {
                Log.i(TAG, "collect: ${it.isLoading} ${it.data.size}")
                _data.emit(it.data)
            }
        }
    }

    fun loadNextPage() {
        loader.loadNextPage()
    }

    fun tryLoadNew() {
        loader.tryLoadNew()
    }

    override fun onCleared() {
        super.onCleared()
        loader.onCleared()
    }

    companion object {
        private const val TAG = "MediaHistoryViewModel"
    }
}