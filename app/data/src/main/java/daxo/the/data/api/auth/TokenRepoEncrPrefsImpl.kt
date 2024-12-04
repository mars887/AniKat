package daxo.the.data.api.auth

import android.content.Context
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import dagger.hilt.android.qualifiers.ApplicationContext
import daxo.core.api.ApiToken
import daxo.core.api.ITokenRepo
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ApiTokenTokenRepoPrefsImpl @Inject constructor(
    @ApplicationContext context: Context
) : ITokenRepo {

    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    private val sharedPreferences = EncryptedSharedPreferences.create(
        "encrypted_api_token_prefs",
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    override fun putToken(token: ApiToken) {
        sharedPreferences.edit {
            putString(TOKEN_KEY, token.token)
        }
    }

    override fun getToken(): ApiToken? {
        return sharedPreferences.getString(TOKEN_KEY, null)?.toToken()
    }

    override fun tokenAvailable(): Boolean {
        return sharedPreferences.contains(TOKEN_KEY)
    }

    override fun clearToken() {
        sharedPreferences.edit {
            remove(TOKEN_KEY)
        }
    }

    companion object {
        private const val TOKEN_KEY = "Token"
        private const val TAG = "TokenRepoEncrPrefsImpl"
    }

    private fun String.toToken(): ApiToken {
        return ApiToken(token = this)
    }

}


