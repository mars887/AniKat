package daxo.the.anikat.fragments.mediaviewinghistory.test

import dagger.hilt.android.scopes.ViewModelScoped
import daxo.core.model.media.ExtendedMediaCardViewed
import daxo.the.data.interfaces.media_store.IMediaViewHistoryRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@ViewModelScoped
class DataLoader @Inject constructor(
    private val mediaViewHistoryRepo: IMediaViewHistoryRepo
) {
    private val scope = CoroutineScope(Dispatchers.IO)
    private val workDispatcher = Dispatchers.IO.limitedParallelism(1)

    private val _state: MutableStateFlow<InnerState> = MutableStateFlow(InnerState())
    val state
        get() = _state.map {
            State(it.data, it.isLoading, it.hasMore)
        }

    fun loadNextPage() {
        launchWork {
            loadNextPage(it)
        }
    }

    fun tryLoadNew() {
        scope.launch {
            _state.value = _state.value.copy(
                loadFirstPage = true
            )
            launchWork {
                loadNextPage(it)
            }
        }

    }

    private fun launchWork(func: suspend (InnerState) -> InnerState) {
        scope.launch(workDispatcher) {
            _state.value = func(_state.value)
        }
    }

    private suspend fun loadNextPage(currentState: InnerState): InnerState {
        if (currentState.isLoading || !currentState.hasMore) return currentState

        return try {
            var nextPage = if (currentState.loadFirstPage) {
                0
            } else {
                currentState.pagesCount + 1
            }

            val loadedItems = mediaViewHistoryRepo.getViewedPaged(nextPage, PER_PAGE)

            if (loadedItems.size < PER_PAGE) nextPage--

            val newData = mutableListOf<ExtendedMediaCardViewed>().apply {
                if (currentState.loadFirstPage) {
                    addAll(loadedItems.filterNotNull())
                    addAll(
                        currentState.data
                            .filterNot {
                                loadedItems.contains(it)
                            })
                } else {
                    addAll(currentState.data)
                    addAll(
                        loadedItems
                            .filterNotNull()
                            .filterNot {
                                currentState.data.contains(it)
                            }
                    )
                }
            }
            launchWork { currentState ->
                currentState.copy(
                    data = newData,
                    pagesCount = if (currentState.loadFirstPage) currentState.pagesCount else nextPage,
                    isLoading = false,
                    loadFirstPage = false,
                )
            }
            currentState.copy(isLoading = true)
        } catch (e: Exception) {
            currentState.copy(isLoading = false)
        }
    }

    fun onCleared() {
        scope.cancel("OnCleared")
    }

    data class State(
        val data: List<ExtendedMediaCardViewed> = listOf(),
        val isLoading: Boolean = false,
        val hasMore: Boolean = true,
    )

    private data class InnerState(
        val data: List<ExtendedMediaCardViewed> = listOf(),
        val pagesCount: Int = -1,
        val isLoading: Boolean = false,
        val loadFirstPage: Boolean = false,
        val hasMore: Boolean = true,
    )

    companion object {
        const val PER_PAGE = 20
        private const val TAG = "DataLoader"
    }
}