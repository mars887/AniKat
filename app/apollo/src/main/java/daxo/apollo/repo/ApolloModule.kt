package daxo.apollo.repo

import android.content.Context
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo.cache.normalized.api.NormalizedCacheFactory
import com.apollographql.apollo.cache.normalized.normalizedCache
import com.apollographql.apollo.cache.normalized.sql.SqlNormalizedCacheFactory
import com.apollographql.apollo.network.okHttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import daxo.apollo.auth.OkHttpAuthenticatedInterceptor
import daxo.core.api.ApiInfo
import okhttp3.OkHttpClient
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApolloModule {

    @Singleton
    @Provides
    fun provideApolloCacheTokenFactory(@ApplicationContext context: Context): NormalizedCacheFactory {
        val sqlCacheFactory = SqlNormalizedCacheFactory(context, "anikatApolloCacheWithToken")
        val memoryCacheFactory = MemoryCacheFactory(maxSizeBytes = 10 * 1024 * 1024)
        return memoryCacheFactory.chain(sqlCacheFactory)
    }

    @Singleton
    @Provides
    fun provideAuthenticatedApolloClient(
        cacheFactory: NormalizedCacheFactory,
        @AuthenticatedOkHttp okHttpClient: OkHttpClient,
    ): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl(ApiInfo.GRAPHQL_URL)
            .normalizedCache(cacheFactory)
            .okHttpClient(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    @AuthenticatedOkHttp
    fun provideAuthenticatedOkHttpClient(
        interceptor: OkHttpAuthenticatedInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }
}



@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthenticatedOkHttp