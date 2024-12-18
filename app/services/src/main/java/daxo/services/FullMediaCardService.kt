package daxo.services

import daxo.core.model.media.enums.MediaType
import daxo.core.model.media.enums.StaffLanguage
import daxo.core.model.media.media.extended.ExtendedMediaCard
import daxo.core.model.media.media.full.FullMediaCard
import daxo.the.data.interfaces.media_get.FMCRequestParams
import daxo.the.data.interfaces.media_get.IFullMediaCardRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FullMediaCardService @Inject constructor(
    private val mediaCardRepo: IFullMediaCardRepo
) {
    operator fun invoke(
        extendedMediaCard: ExtendedMediaCard,
        staffLanguage: StaffLanguage = StaffLanguage.JAPANESE
    ): Flow<FullMediaCard> = flow {
        mediaCardRepo.getFullMediaCardFlow(
            extendedMediaCard.mediaId,
            extendedMediaCard.mediaType ?: MediaType.UNKNOWN__,
            params = FMCRequestParams(voiceActorsLanguage = staffLanguage)
        ).collect {
            emit(it)
        }
    }
}