package daxo.the.anikat.core.di.modules

import android.content.Context
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo.cache.normalized.api.NormalizedCacheFactory
import com.apollographql.apollo.cache.normalized.normalizedCache
import com.apollographql.apollo.cache.normalized.sql.SqlNormalizedCacheFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class ApolloModule {

    @Singleton
    @Provides
    fun provideApolloCacheFactory(context: Context): NormalizedCacheFactory {
        val sqlCacheFactory = SqlNormalizedCacheFactory(context, "anikatApolloCache")
        val memoryCacheFactory = MemoryCacheFactory(maxSizeBytes = 10 * 1024 * 1024)
        return memoryCacheFactory.chain(sqlCacheFactory)
    }

    @Singleton
    @Provides
    fun provideApolloClient(cacheFactory: NormalizedCacheFactory): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl("https://graphql.anilist.co")
            .normalizedCache(cacheFactory)
            .build()
    }
}