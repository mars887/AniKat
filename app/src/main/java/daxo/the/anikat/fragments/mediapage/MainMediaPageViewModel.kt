package daxo.the.anikat.fragments.mediapage

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.savedstate.SavedStateRegistryOwner
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import daxo.core.model.media.ExtendedMediaCard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainMediaPageViewModel (
    private val onMediaOpenedUseCase: OnMediaOpenedUseCase,
    private val initialCard: ExtendedMediaCard
) : ViewModel() {

    fun saveOpenState() {
        viewModelScope.launch(Dispatchers.IO) {
            onMediaOpenedUseCase(initialCard)
        }
    }
}

class MainMediaPageViewModelFactoryFactory @Inject constructor(
    private val onMediaOpenedUseCase: OnMediaOpenedUseCase,
) {
    fun create(initialCard: ExtendedMediaCard): MainMediaPageFragment.MainMediaPageViewModelFactory {
        return MainMediaPageFragment.MainMediaPageViewModelFactory(onMediaOpenedUseCase, initialCard)
    }
}