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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMediaBoardsUseCase @Inject constructor(
    private val mediaRepo: MediaRepo,
    private val settings: SettingsRepo,
    private val mediaContent: ExploreMediaPagesInfo
) {
    private val currentlyPaginating: MutableSet<ExploreMediaPagesInfo.MediaTypes> = mutableSetOf()

    suspend fun paginateByTag(
        type: MediaType,
        tag: ExploreMediaPagesInfo.MediaTypes
    ): Flow<MediaLineData> = flow {
        if (currentlyPaginating.contains(tag)) return@flow
        else currentlyPaginating += tag

        val titleType = settings.getTitleLanguage()
        val mediaLine = mediaContent.pages.find { it.pageKeys[type] == tag } ?: pageByTagNotFound(tag)

        mediaRepo.loadContentBy(
            pageConfig = NextPage(tag, false),
            params = QueryParams(
                type = type,
                sort = mediaLine.sort
            )
        )?.convertToMediaLineDataNamed(
            lineName = mediaLine.lineName,
            mediaType = type,
            titleType = titleType,
            tag = tag
        )?.let {
            emit(it)
        }
        currentlyPaginating -= tag
    }

    suspend operator fun invoke(type: MediaType): Flow<List<MediaLineData>> = flow {
        val titleType = settings.getTitleLanguage()
        val data = mutableListOf<MediaLineData>()

        mediaContent.pages.forEach { it ->
            mediaRepo.loadContentBy(
                pageConfig = NextPage(it.pageKeys[type] ?: throwUMT(), true),
                params = QueryParams(
                    type = type,
                    sort = it.sort
                )
            )?.convertToMediaLineDataNamed(
                lineName = it.lineName,
                mediaType = type,
                titleType = titleType,
                tag = it.pageKeys[type] ?: tagByPageKeysNotFound(it.pageKeys)
            )?.let {
                data += it
                emit(data.toList())
            }
        }
    }

    private fun tagByPageKeysNotFound(pageKeys: Map<MediaType, ExploreMediaPagesInfo.MediaTypes>): Nothing =
        throw IllegalStateException("Tag by pageKeys $pageKeys not found")

    private fun throwUMT(): Nothing = throw IllegalStateException("Unknown media type")
    private fun pageByTagNotFound(tag: ExploreMediaPagesInfo.MediaTypes): Nothing =
        throw IllegalStateException("Page by media page not found - $tag")
}