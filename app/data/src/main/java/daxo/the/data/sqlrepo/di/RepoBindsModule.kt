package daxo.the.data.sqlrepo.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepoBindsModule {

//    @Binds
//    fun bindsIBasicMediaCacheRepo(basicMediaCacheRepo: BasicMediaCacheRepo): IBasicMediaCacheRepo
//
//    @Binds
//    fun bindsIMediaViewHistoryRepo(mediaViewHistoryRepo: MediaViewHistoryRepo): IMediaViewHistoryRepo
}