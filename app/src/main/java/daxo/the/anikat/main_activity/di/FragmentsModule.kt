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
    fun provideExploreAnimeFragment(): ExploreAnimeFragment {
        return ExploreAnimeFragment()
    }

    @Provides
    fun provideExploreMangaFragment(): ExploreMangaFragment {
        return ExploreMangaFragment()
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