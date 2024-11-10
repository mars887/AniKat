package daxo.the.data.sqlrepo.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import daxo.the.data.repoimpl.BasicMediaCacheRepo
import daxo.the.data.repoimpl.MediaViewHistoryRepo
import daxo.the.domain.irepo.IBasicMediaCacheRepo
import daxo.the.domain.irepo.IMediaViewHistoryRepo


@Module
@InstallIn(SingletonComponent::class)
interface RepoBindsModule {

    @Binds
    fun bindsIBasicMediaCacheRepo(basicMediaCacheRepo: BasicMediaCacheRepo): IBasicMediaCacheRepo

    @Binds
    fun bindsIMediaViewHistoryRepo(mediaViewHistoryRepo: MediaViewHistoryRepo): IMediaViewHistoryRepo
}