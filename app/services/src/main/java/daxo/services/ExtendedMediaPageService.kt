package daxo.services

import daxo.the.data.interfaces.media_get.LoadMediaPagesParams
import daxo.the.data.interfaces.media_get.IExtendedMediaRepo
import daxo.the.data.interfaces.media_store.IExtendedMediaCacheRepo
import daxo.core.model.media.media.extended.ExtendedMediaCard
import daxo.core.model.media.media.extended.ExtendedMediaCardList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.Executors
import javax.inject.Inject

class ExtendedMediaPageService @Inject constructor(
    private val extendedMediaRepo: IExtendedMediaRepo,
    private val extendedMediaCacheRepo: IExtendedMediaCacheRepo
) {
    private val serviceScope = CoroutineScope(Executors.newSingleThreadExecutor().asCoroutineDispatcher())
    private val currentlyPaginating: MutableMap<String, Boolean> = mutableMapOf()
    private val mapMutex = Mutex()

    suspend fun requestPaginateFor(listToPaginate: ExtendedMediaCardList): ExtendedMediaCardList? {
        mapMutex.withLock {
            if (currentlyPaginating[listToPaginate.requestData] == true) return null
            currentlyPaginating[listToPaginate.requestData] = true
        }

        val request = LoadMediaPagesParams.nextPageRequest(listToPaginate.requestData)

        val cards = extendedMediaRepo.loadPages(request) ?: return null
        addCache(cards) // cache update

        val newList = mutableListOf<ExtendedMediaCard>().let {
            it.addAll(listToPaginate.cards)
            it.addAll(cards)
            it.distinctBy { it.mediaId }
        }

        mapMutex.withLock {
            currentlyPaginating.remove(listToPaginate.requestData)
        }
        return ExtendedMediaCardList(
            request.toRequestData(),
            listToPaginate.listName,
            newList
        )
    }

    suspend fun loadExtendedMediaCardList(params: LoadMediaPagesParams, listName: String): ExtendedMediaCardList? {
        if (params.page == -1) params.page = 1
        val cards = extendedMediaRepo.loadPages(params) ?: return null
        addCache(cards)
        val list = ExtendedMediaCardList(params.toRequestData(), listName, cards)
        return list
    }

    private suspend fun addCache(cards: List<ExtendedMediaCard>) {
        serviceScope.launch {
            extendedMediaCacheRepo.add(cards)
        }
    }
}