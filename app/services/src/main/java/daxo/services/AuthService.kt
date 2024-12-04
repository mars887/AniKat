package daxo.services

import android.util.Log
import daxo.core.api.ApiInfo
import daxo.core.api.ApiToken
import daxo.core.api.ApiAppSecretData
import daxo.core.api.AuthConfig
import daxo.core.api.ITokenRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException
import javax.inject.Inject


class AuthService @Inject constructor(
    private val client: OkHttpClient,
    private val tokenRepo: ITokenRepo
) {

    suspend fun fetchAccessToken(code: String): Flow<AuthTokenFetchStatus> = flow {
        try {
            val requestBody = FormBody.Builder()
                .add("grant_type", "authorization_code")
                .add("client_id", ApiAppSecretData.CLIENT_ID)
                .add("client_secret", ApiAppSecretData.CLIENT_SECRET)
                .add("redirect_uri", AuthConfig.CALLBACK_URL)
                .add("code", code)
                .build()

            val request = Request.Builder()
                .url(ApiInfo.TOKEN_URI)
                .post(requestBody)
                .build()

            emit(AuthTokenFetchStatus.StartFetching()) // StartFetching

            client.newCall(request).execute().use { response ->
                Log.i("AUTH", "fetchAccessToken: $response")
                if (!response.isSuccessful) {
                    emit(AuthTokenFetchStatus.Error("response incorrect")) // Error
                }

                val json = response.body?.string() ?: throw IOException("Empty response body")
                val token = AuthTokenFetchStatus.Successful(parseToken(json))

                tokenRepo.putToken(token.token) // saving token in repo
                emit(token) // Success
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(AuthTokenFetchStatus.Error(e.message)) // Error
        }
    }

    private fun parseToken(json: String): ApiToken {
        Log.i(TAG, "parseToken: $json")
        val jsonObject = JSONObject(json)
        return ApiToken(
            token = jsonObject.getString("access_token"),
        )
    }

    sealed class AuthTokenFetchStatus {
        class StartFetching : AuthTokenFetchStatus()
        class Successful(val token: ApiToken) : AuthTokenFetchStatus()
        class Error(val message: String?) : AuthTokenFetchStatus()
    }

    companion object {
        private const val TAG = "AuthService"
    }
}