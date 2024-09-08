package daxo.the.anikat.main_activity.di

import dagger.Module
import dagger.Provides
import daxo.the.anikat.fragments.browse.ExploreAnimeFragment
import daxo.the.anikat.fragments.browse.ExploreMangaFragment
import daxo.the.anikat.fragments.browse.data.viewmodel.ExploreViewModel
import daxo.the.anikat.fragments.mediapage.MainMediaPageFragment
import daxo.the.anikat.fragments.mediapage.MainMediaPageViewModel
import daxo.the.anikat.fragments.profile.ProfileFragment
import daxo.the.anikat.fragments.profile.ProfileViewModel
import daxo.the.anikat.tests.navigation_test.FragmentsNavigator

@Module
class FragmentsModule {

    @Provides
    fun provideExploreAnimeFragment(fn: FragmentsNavigator): ExploreAnimeFragment {
        return ExploreAnimeFragment(
            fn.getVM(ExploreViewModel::class, "ExploreAnimeFragment"), fn
        )
    }

    @Provides
    fun provideExploreMangaFragment(fn: FragmentsNavigator): ExploreMangaFragment {
        return ExploreMangaFragment(
            fn.getVM(ExploreViewModel::class, "ExploreMangaFragment"), fn
        )
    }

    @Provides
    fun provideProfileFragment(fn: FragmentsNavigator): ProfileFragment {
        return ProfileFragment(fn.getVM(ProfileViewModel::class))
    }

    @Provides
    fun provideMainMediaPageFragment(fn: FragmentsNavigator): MainMediaPageFragment {
        return MainMediaPageFragment(fn.getVM(MainMediaPageViewModel::class))
    }
}