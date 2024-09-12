package daxo.the.anikat.fragments.browse.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import daxo.the.anikat.core.viewModels.ViewModelKey
import daxo.the.anikat.fragments.browse.data.entity.ExploreMediaPageInfo
import daxo.the.anikat.fragments.browse.data.entity.ExploreMediaPagesInfo
import daxo.the.anikat.fragments.browse.data.viewmodel.ExploreViewModel
import daxo.the.anikat.type.MediaSort
import daxo.the.anikat.type.MediaType
import javax.inject.Singleton

@Module
class ExploreModule {

    @Provides
    fun provideExploreMediaPagesInfo(): ExploreMediaPagesInfo {
        return ExploreMediaPagesInfo(
            listOf(
                ExploreMediaPageInfo(
                    pageKeys = mapOf(
                        MediaType.ANIME to ExploreMediaPagesInfo.MediaTypes.EXPLORE_ANIME_1,
                        MediaType.MANGA to ExploreMediaPagesInfo.MediaTypes.EXPLORE_MANGA_1
                    ),
                    sort = listOf(MediaSort.TRENDING_DESC, MediaSort.POPULARITY_DESC),
                    "Trending now"
                ),
                ExploreMediaPageInfo(
                    pageKeys = mapOf(
                        MediaType.ANIME to ExploreMediaPagesInfo.MediaTypes.EXPLORE_ANIME_2,
                        MediaType.MANGA to ExploreMediaPagesInfo.MediaTypes.EXPLORE_MANGA_2
                    ),
                    sort = listOf(MediaSort.POPULARITY_DESC),
                    "Popular all time"
                ),
                ExploreMediaPageInfo(
                    pageKeys = mapOf(
                        MediaType.ANIME to ExploreMediaPagesInfo.MediaTypes.EXPLORE_ANIME_3,
                        MediaType.MANGA to ExploreMediaPagesInfo.MediaTypes.EXPLORE_MANGA_3
                    ),
                    sort = listOf(MediaSort.SCORE_DESC),
                    "top 100"
                ),
            )
        )
    }
}