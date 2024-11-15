package daxo.the.anikat.fragments.browse.usecases

import daxo.services.BasicMediaPageService
import daxo.the.domain.model.media.BasicMediaCard
import daxo.the.domain.model.media.BasicMediaCardList
import javax.inject.Inject

class PaginateMediaLineUseCase @Inject constructor(
    private val basicMediaPageService: BasicMediaPageService
) {
    suspend operator fun invoke(basicMediaCardList: BasicMediaCardList): BasicMediaCardList? {
        return basicMediaPageService.requestPaginateFor(basicMediaCardList)
    }
}