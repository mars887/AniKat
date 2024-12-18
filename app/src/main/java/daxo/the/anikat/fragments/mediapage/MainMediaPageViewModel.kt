package daxo.the.anikat.fragments.mediapage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import daxo.core.images.ImageInfoModel
import daxo.core.model.media.media.extended.ExtendedMediaCard
import daxo.core.model.media.media.full.FullMediaCard
import daxo.services.FullMediaCardService
import daxo.services.MediaCardInteractionService
import daxo.the.anikat.fragments.dialogs.imaged.ImageShareLoadDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainMediaPageViewModel(
    private val onMediaOpenedUseCase: OnMediaOpenedUseCase,
    private val fullMediaCardService: FullMediaCardService,
    private val initialCard: ExtendedMediaCard,
    private val mediaCardInteractionService: MediaCardInteractionService,
) : ViewModel() {

    fun saveOpenState() {
        viewModelScope.launch(Dispatchers.IO) {
            onMediaOpenedUseCase(initialCard)
        }
    }

    fun openImageLongClickMenu(fragment: MainMediaPageFragment, imageInfo: ImageInfoModel) {
        val dialog = ImageShareLoadDialog.newInstance(imageInfo, fragment as ImageShareLoadDialog.SelectedOptionListener)
        dialog.show(fragment.parentFragmentManager, null)
    }

    fun requireFullMediaCard(): Flow<FullMediaCard> {
        return fullMediaCardService.invoke(initialCard)
    }
}


// factories
class MainMediaPageViewModelFactoryFactory @Inject constructor(
    private val onMediaOpenedUseCase: OnMediaOpenedUseCase,
    private val fullMediaCardService: FullMediaCardService,
    private val mediaCardInteractionService: MediaCardInteractionService
) {
    fun create(initialCard: ExtendedMediaCard): MainMediaPageViewModelFactory {
        return MainMediaPageViewModelFactory(
            onMediaOpenedUseCase,
            fullMediaCardService,
            initialCard,
            mediaCardInteractionService
        )
    }
}

@Suppress("UNCHECKED_CAST")
class MainMediaPageViewModelFactory(
    private val onMediaOpenedUseCase: OnMediaOpenedUseCase,
    private val fullMediaCardService: FullMediaCardService,
    private val initialMediaCard: ExtendedMediaCard,
    private val mediaCardInteractionService: MediaCardInteractionService
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainMediaPageViewModel(
            onMediaOpenedUseCase,
            fullMediaCardService,
            initialMediaCard,
            mediaCardInteractionService
        ) as T
    }
}