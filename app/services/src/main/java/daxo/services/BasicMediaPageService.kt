package daxo.services

import android.util.Log
import daxo.the.data.api.explore.LoadMediaPagesParams
import daxo.the.data.interfaces.media_get.IBasicMediaRepo
import daxo.the.data.interfaces.media_store.IBasicMediaCacheRepo
import daxo.the.domain.model.media.BasicMediaCard
import daxo.the.domain.model.media.BasicMediaCardList
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

class BasicMediaPageService @Inject constructor(
    private val basicMediaRepo: IBasicMediaRepo,
    private val basicMediaCacheRepo: IBasicMediaCacheRepo
) {
    private val currentlyPaginating: MutableMap<String, Boolean> = mutableMapOf()
    private val mapMutex = Mutex()

    /**
     * returns new BasicMediaPage with paginated media or null if not loaded
     */
    suspend fun requestPaginateFor(listToPaginate: BasicMediaCardList): BasicMediaCardList? {
        mapMutex.withLock {
            if (currentlyPaginating[listToPaginate.requestData] == true) return null
            currentlyPaginating[listToPaginate.requestData] = true
        }

        val request = LoadMediaPagesParams.nextPageRequest(listToPaginate.requestData)

        val cards = basicMediaRepo.loadPages(request)
        addCache(cards) // cache update

        val newList = mutableListOf<BasicMediaCard>().let {
            it.addAll(listToPaginate.cards)
            it.addAll(cards)
            it.distinctBy { it.mediaId }
        }

        mapMutex.withLock {
            currentlyPaginating.remove(listToPaginate.requestData)
        }
        return BasicMediaCardList(
            request.toRequestData(),
            listToPaginate.listName,
            newList
        )
    }

    /**
     * load content by params
     */
    suspend fun loadBasicMediaCardList(params: LoadMediaPagesParams, listName: String): BasicMediaCardList {
        if (params.page == -1) params.page = 1
        val cards = basicMediaRepo.loadPages(params)
        addCache(cards) // cache update
        val list = BasicMediaCardList(params.toRequestData(), listName, cards)
        return list
    }

    private fun addCache(cards: List<BasicMediaCard>) {
        //basicMediaCacheRepo.add(cards) todo not impl
    }

    companion object {
        private const val TAG = "BasicMediaPageService"
    }
}