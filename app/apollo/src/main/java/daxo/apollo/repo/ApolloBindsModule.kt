package daxo.apollo.repo

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import daxo.the.data.interfaces.media_get.IBasicMediaRepo

@Module
@InstallIn(SingletonComponent::class)
interface ApolloBindsModule {

    @Binds
    fun bindsBasicMediaRepoApolloImpl(apolloImpl: BasicMediaRepoApolloImpl): IBasicMediaRepo
}