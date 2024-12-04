package daxo.the.anikat.fragments.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import daxo.core.model.profile.MainProfileData
import daxo.the.data.interfaces.profile.IMainProfileDataRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val mainProfileDataRepo: IMainProfileDataRepo
) : ViewModel() {

    private val _mainDataState: MutableStateFlow<MainProfileData?> = MutableStateFlow(null)
    val mainDataState get() = _mainDataState.asStateFlow()

    init {
        viewModelScope.launch {
            mainProfileDataRepo.loadProfileFlow().collect {
                _mainDataState.value = it
            }
        }
    }
}