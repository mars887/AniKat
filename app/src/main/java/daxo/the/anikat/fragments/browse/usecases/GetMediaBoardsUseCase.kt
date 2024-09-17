package daxo.the.anikat.fragments.browse.usecases

import daxo.the.anikat.fragments.browse.data.convertToMediaLineDataNamed
import daxo.the.anikat.fragments.browse.data.entity.MediaLineData
import daxo.the.anikat.core.repo.MediaRepo
import daxo.the.anikat.core.repo.PageConfig.NextPage
import daxo.the.anikat.core.repo.QueryParams
import daxo.the.anikat.core.repo.SettingsRepo
import daxo.the.anikat.fragments.browse.data.entity.ExploreMediaPagesInfo
import daxo.the.anikat.type.MediaSort
import daxo.the.anikat.type.MediaType
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.concurrent.thread

class GetMediaBoardsUseCase @Inject constructor(
    private val mediaRepo: MediaRepo,
    private val settings: SettingsRepo,
    private val mediaContent: ExploreMediaPagesInfo
) {
    private val currentlyPaginating: MutableSet<ExploreMediaPagesInfo.MediaTypes> = mutableSetOf()

    suspend fun paginateByTag(
        type: MediaType,
        lineData: MediaLineData,
        func: suspend (MediaLineData, Boolean) -> Unit
    ) {
        val tag = lineData.tag
        if (currentlyPaginating.contains(tag)) return
        else currentlyPaginating += tag

        val titleType = settings.getTitleLanguage()
        val mediaLine =
            mediaContent.pages.find { it.pageKeys[type] == tag } ?: pageByTagNotFound(tag)


        mediaRepo.loadContentBy(
            pageConfig = NextPage(tag, false),
            params = QueryParams(
                type = type,
                sort = mediaLine.sort
            )
        ).onCompletion {
            currentlyPaginating -= tag
        }.collect {
            it.first.convertToMediaLineDataNamed(
                lineName = mediaLine.lineName,
                mediaType = type,
                titleType = titleType,
                tag = tag
            ).let { data ->
                func(data, it.second)
            }
        }

    }

    suspend operator fun invoke(type: MediaType): Flow<List<MediaLineData>> = channelFlow {
        val titleType = settings.getTitleLanguage()
        val data = mutableListOf<MediaLineData>()

        println("invoked getMediaBoardsUseCase")

        coroutineScope {
            mediaContent.pages.forEach {
                launch {
                    mediaRepo.loadContentBy(
                        pageConfig = NextPage(it.pageKeys[type] ?: throwUMT(), true),
                        params = QueryParams(
                            type = type,
                            sort = it.sort
                        )
                    ).collect { input ->
                        input.first.convertToMediaLineDataNamed(
                            lineName = it.lineName,
                            mediaType = type,
                            titleType = titleType,
                            tag = it.pageKeys[type] ?: tagByPageKeysNotFound(it.pageKeys)
                        ).let { lineData ->
                            var isChanged = false
                            val findex = data.indexOfFirst { it.tag == lineData.tag }
                            if (findex == -1) {
                                data += lineData
                                isChanged = true
                                println("adding data")
                            } else {
                                isChanged = data[findex] != lineData
                                data[findex] = lineData
                                println("setting data")
                            }
                            if (isChanged) {
                                println("emitting ${data.map { it.lineName }}")
                                send(data.toList()) // TODO test
                            }
                        }
                    }
                }
            }
        }
    }

    private fun tagByPageKeysNotFound(pageKeys: Map<MediaType, ExploreMediaPagesInfo.MediaTypes>): Nothing =
        throw IllegalStateException("Tag by pageKeys $pageKeys not found")

    private fun throwUMT(): Nothing = throw IllegalStateException("Unknown media type")
    private fun pageByTagNotFound(tag: ExploreMediaPagesInfo.MediaTypes): Nothing =
        throw IllegalStateException("Page by media page not found - $tag")
}