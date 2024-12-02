package daxo.apollo.repo

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import daxo.apollo.auth.CheckTokenService
import daxo.apollo.profile.maind.MainProfileDataRepoImpl
import daxo.apollo.repo.extended_media.ExtendedMediaRepoApolloImpl
import daxo.the.data.api.auth.ICheckTokenService
import daxo.the.data.interfaces.media_get.IExtendedMediaRepo
import daxo.the.data.interfaces.profile.IMainProfileDataRepo

@Module
@InstallIn(SingletonComponent::class)
interface ApolloBindsModule {
    @Binds
    fun bindsExtendedMediaRepoApolloImpl(apolloImpl: ExtendedMediaRepoApolloImpl): IExtendedMediaRepo

    @Binds
    fun bindsMainProfileDataRepoImpl(apolloImpl: MainProfileDataRepoImpl): IMainProfileDataRepo

    @Binds
    fun bindsCheckTokenService(apolloImpl: CheckTokenService): ICheckTokenService
}