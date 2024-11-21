package daxo.the.anikat.fragments.browse.usecases

import daxo.services.ExtendedMediaPageService
import daxo.the.anikat.fragments.browse.data.objects.ExploreCardListsInfo
import daxo.the.data.api.explore.LoadMediaPagesParams
import daxo.core.model.media.ExtendedMediaCardList
import daxo.core.model.media.enums.MediaType
import javax.inject.Inject

class LoadMediaCardListsUseCase @Inject constructor(
    private val extendedMediaPageService: ExtendedMediaPageService
) {
    suspend operator fun invoke(mediaType: MediaType): List<ExtendedMediaCardList> {
        val lists = mutableListOf<ExtendedMediaCardList>()
        ExploreCardListsInfo.info[mediaType]?.forEach { info ->
            val params = LoadMediaPagesParams(
                type = mediaType,
                sort = info.sort
            )
            extendedMediaPageService.loadExtendedMediaCardList(params,info.lineName)?.let {
                lists += it
            }
        }
        return lists
    }
}
