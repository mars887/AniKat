package daxo.the.anikat.fragments.browse.usecases

import daxo.services.BasicMediaPageService
import daxo.the.anikat.fragments.browse.data.objects.ExploreCardListsInfo
import daxo.the.data.api.explore.LoadMediaPagesParams
import daxo.the.domain.model.media.BasicMediaCardList
import daxo.the.domain.model.media.enums.MediaType
import javax.inject.Inject

class LoadMediaCardListsUseCase @Inject constructor(
    private val basicMediaPageService: BasicMediaPageService
) {
    suspend operator fun invoke(mediaType: MediaType): List<BasicMediaCardList> {
        val lists = mutableListOf<BasicMediaCardList>()
        ExploreCardListsInfo.info[mediaType]?.forEach { info ->
            val params = LoadMediaPagesParams(
                type = mediaType,
                sort = info.sort
            )
            lists += basicMediaPageService.loadBasicMediaCardList(params,info.lineName)
        }
        return lists
    }
}
