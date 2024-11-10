package daxo.the.data.sqlrepo.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import daxo.the.data.sqlrepo.dao.BasicMediaCacheDao
import daxo.the.data.sqlrepo.dao.MediaViewHistoryDao
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
    fun provideBasicMediaCacheDao(mediaPageStorage: MediaPageStorage): BasicMediaCacheDao {
        return mediaPageStorage.basicMediaCacheDao()
    }

    @Provides
    fun provideMediaViewHistoryDao(mediaPageStorage: MediaPageStorage): MediaViewHistoryDao {
        return mediaPageStorage.mediaViewHistoryDao()
    }
}