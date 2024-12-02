package daxo.the.anikat.fragments.mediapage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import daxo.core.images.ImageInfoModel
import daxo.core.model.media.ExtendedMediaCard
import daxo.the.anikat.fragments.dialogs.imaged.ImageShareLoadDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainMediaPageViewModel(
    private val onMediaOpenedUseCase: OnMediaOpenedUseCase,
    private val initialCard: ExtendedMediaCard
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
}

class MainMediaPageViewModelFactoryFactory @Inject constructor(
    private val onMediaOpenedUseCase: OnMediaOpenedUseCase,
) {
    fun create(initialCard: ExtendedMediaCard): MainMediaPageViewModelFactory {
        return MainMediaPageViewModelFactory(onMediaOpenedUseCase, initialCard)
    }
}

@Suppress("UNCHECKED_CAST")
class MainMediaPageViewModelFactory(
    private val onMediaOpenedUseCase: OnMediaOpenedUseCase,
    private val initialMediaCard: ExtendedMediaCard
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainMediaPageViewModel(onMediaOpenedUseCase, initialMediaCard) as T
    }
}