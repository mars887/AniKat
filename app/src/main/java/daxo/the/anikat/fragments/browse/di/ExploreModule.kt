package daxo.the.anikat.fragments.browse.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import daxo.the.anikat.fragments.browse.data.entity.ExploreMediaPageInfo
import daxo.the.anikat.fragments.browse.data.entity.ExploreMediaPagesInfo
import daxo.the.anikat.type.MediaSort
import daxo.the.anikat.type.MediaType

@Module
@InstallIn(SingletonComponent::class)
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