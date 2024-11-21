package daxo.apollo.repo

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import daxo.apollo.repo.extended_media.ExtendedMediaRepoApolloImpl
import daxo.the.data.interfaces.media_get.IExtendedMediaRepo

@Module
@InstallIn(SingletonComponent::class)
interface ApolloBindsModule {
    @Binds
    fun bindsExtendedMediaRepoApolloImpl(apolloImpl: ExtendedMediaRepoApolloImpl): IExtendedMediaRepo
}