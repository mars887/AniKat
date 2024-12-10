package daxo.the.anikat.fragments.mediapage

import android.util.Log
import daxo.the.data.interfaces.media_store.IMediaViewHistoryRepo
import daxo.core.model.media.media.extended.ExtendedMediaCard
import javax.inject.Inject

class OnMediaOpenedUseCase @Inject constructor(
    private val mediaHistoryRepo: IMediaViewHistoryRepo
) {
    suspend operator fun invoke(data: ExtendedMediaCard) {
        Log.i("OnMediaOpenedUseCase", "OnMediaOpenedUseCase: ${data.title?.english}")
        mediaHistoryRepo.applyMediaOpened(data)
    }
}