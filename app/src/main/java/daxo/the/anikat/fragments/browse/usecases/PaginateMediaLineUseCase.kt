package daxo.the.anikat.fragments.browse.usecases

import daxo.services.ExtendedMediaPageService
import daxo.core.model.media.media.extended.ExtendedMediaCardList
import javax.inject.Inject

class PaginateMediaLineUseCase @Inject constructor(
    private val extendedMediaPageService: ExtendedMediaPageService
) {
    suspend operator fun invoke(mediaList: ExtendedMediaCardList): ExtendedMediaCardList? {
        return extendedMediaPageService.requestPaginateFor(mediaList)
    }
}