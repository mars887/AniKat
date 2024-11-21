package daxo.the.data.sqlrepo.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import daxo.the.data.interfaces.media_store.IExtendedMediaCacheRepo
import daxo.the.data.interfaces.media_store.IMediaViewHistoryRepo
import daxo.the.data.sqlrepo.dao.ExtendedMediaCacheDao
import daxo.the.data.sqlrepo.dao.MediaViewHistoryDao
import daxo.the.data.sqlrepo.impl.ExtendedMediaCacheRepoRoomImpl
import daxo.the.data.sqlrepo.impl.MediaViewHistoryRepoRoomImpl
import daxo.the.data.sqlrepo.repo.MediaPageStorage
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Singleton
    @Provides
    fun provideMediaPageStorage(@ApplicationContext context: Context): MediaPageStorage {
        return Room.databaseBuilder(context, MediaPageStorage::class.java, "media_page_storage").build()
    }

    @Provides
    fun provideMediaViewHistoryDao(mediaPageStorage: MediaPageStorage): MediaViewHistoryDao {
        return mediaPageStorage.mediaViewHistoryDao()
    }

    @Provides
    fun provideExtendedMadiaCacheDao(mediaPageStorage: MediaPageStorage): ExtendedMediaCacheDao {
        return mediaPageStorage.extendedMediaCacheDao()
    }
}

@Module
@InstallIn(SingletonComponent::class)
interface BindsRoomModule {

    @Binds
    abstract fun bindsIMediaViewHistoryRepo(mediaViewHistoryRepoRoomImpl: MediaViewHistoryRepoRoomImpl): IMediaViewHistoryRepo

    @Binds
    abstract fun bindsIExtendedMediaCacheRepo(extendedMediaCacheRepoRoomImpl: ExtendedMediaCacheRepoRoomImpl): IExtendedMediaCacheRepo
}