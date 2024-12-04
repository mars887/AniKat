package daxo.the.anikat.fragments.profile.unlogged

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import daxo.apollo.auth.CheckTokenService
import daxo.core.api.ApiInfo
import daxo.core.api.ApiAppSecretData
import daxo.core.api.AuthConfig
import daxo.services.AuthService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AuthProfileViewModel @Inject constructor(
    private val authService: AuthService,
    private val tokenValidator: CheckTokenService
) : ViewModel() {

    private val authState: MutableStateFlow<AuthState> = MutableStateFlow(AuthState.NotStarted)
    fun getAuthState(): StateFlow<AuthState> = authState.asStateFlow()


    fun handleAuthResponseIntent(dataIntent: Intent) {
        viewModelScope.launch {
            dataIntent.data?.let { uri ->
                val authCode = uri.getQueryParameter("code")
                if (authCode != null) {
                    authState.value = AuthState.FetchingToken
                    Log.i("AUTH", "handleAuthResponseIntent: $authCode")
                    exchangeCodeForToken(authCode)
                } else {
                    authState.value = AuthState.Error("Authorization code not found in URI")
                }
            }
        }
    }

    private suspend fun exchangeCodeForToken(authCode: String) {
        withContext(Dispatchers.IO) {
            authService.fetchAccessToken(authCode).collect {

                when (it) {
                    is AuthService.AuthTokenFetchStatus.Successful -> {
                        authState.value = AuthState.ValidatingToken
                        validateToken()
                    }

                    is AuthService.AuthTokenFetchStatus.Error -> {
                        authState.value = AuthState.Error(it.message)
                    }

                    else -> {}
                }
            }
        }
    }

    private suspend fun validateToken() {
        tokenValidator.checkToken(true)
            .onFailure {
                authState.value = AuthState.Error("token validating error")
            }.onSuccess {
                authState.value = AuthState.Successful
            }
    }

    fun createLoginCustomTab(): Pair<CustomTabsIntent, Uri> {
        val authUri = Uri.parse(ApiInfo.AUTH_URI)
            .buildUpon()
            .appendQueryParameter("client_id", ApiAppSecretData.CLIENT_ID)
            .appendQueryParameter("redirect_uri", AuthConfig.CALLBACK_URL)
            .appendQueryParameter("response_type", "code")
            .build()

        val intent = CustomTabsIntent.Builder().build()

        return intent to authUri
    }

    fun resetState() {
        authState.value = AuthState.NotStarted
    }


    sealed class AuthState {
        data object NotStarted : AuthState()
        data object FetchingToken : AuthState()
        data object ValidatingToken : AuthState()
        data object Successful : AuthState()
        data class Error(val message: String?) : AuthState()
    }

    companion object {
        private const val TAG = "AuthProfileViewModel"
    }
}