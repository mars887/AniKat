package daxo.apollo.auth

import daxo.core.api.ITokenRepo
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class OkHttpAuthenticatedInterceptor @Inject constructor(
    private val tokenStorage: ITokenRepo
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        return tokenStorage.getToken()?.token?.let {
            val authenticatedRequest = request.newBuilder()
                .addHeader("Authorization", "Bearer $it")
                .build()

            chain.proceed(authenticatedRequest)
        } ?: chain.proceed(request)
    }
}