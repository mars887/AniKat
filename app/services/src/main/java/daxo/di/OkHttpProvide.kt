package daxo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient


@Module
@InstallIn(SingletonComponent::class)
class OkHttpProvide {

    @Provides
    fun provideDefaultOkHttpClient(): OkHttpClient {
        return OkHttpClient()
    }
}