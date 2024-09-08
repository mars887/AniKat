package daxo.the.anikat.main_activity.di

import androidx.fragment.app.FragmentManager
import dagger.Module
import dagger.Provides
import daxo.the.anikat.R
import daxo.the.anikat.core.di.scopes.ActivityScope
import daxo.the.anikat.fragments.browse.ExploreAnimeFragment
import daxo.the.anikat.fragments.browse.ExploreMangaFragment
import daxo.the.anikat.fragments.browse.data.viewmodel.ExploreViewModel
import daxo.the.anikat.fragments.mediapage.MainMediaPageFragment
import daxo.the.anikat.fragments.mediapage.MainMediaPageViewModel
import daxo.the.anikat.fragments.profile.ProfileFragment
import daxo.the.anikat.fragments.profile.ProfileViewModel
import daxo.the.anikat.main_activity.MainActivity
import daxo.the.anikat.tests.navigation_test.FragmentRules
import daxo.the.anikat.tests.navigation_test.FragmentsNavigator
import daxo.the.anikat.tests.navigation_test.ViewModelProvider
import daxo.the.anikat.tests.navigation_test.FragmentsNavigatorElement as FNE

@Module
class FragmentsNavigatorModule(
    private val supportFragmentManager: FragmentManager,
    private val mainActivity: MainActivity,
) {

    @Provides
    @ActivityScope
    fun provideViewModelProvider(): ViewModelProvider {
        return ViewModelProvider(
            mapOf(
                ExploreViewModel::class to { mainActivity.activityComponent.getExploreViewModel() },
                ProfileViewModel::class to { mainActivity.activityComponent.getProfileViewModel() },
                MainMediaPageViewModel::class to { mainActivity.activityComponent.getMainMediaPageViewModel() }
            )
        )
    }

    @Provides
    @ActivityScope
    fun provideFragmentsNavigator(viewModelProvider: ViewModelProvider): FragmentsNavigator {
        return FragmentsNavigator(
            viewModelProvider,
            supportFragmentManager,
            listOf(
                FNE(
                    R.id.exploreAnimeFragment,
                    ExploreAnimeFragment::class,
                    FragmentRules(
                        FragmentRules.BackstackPolicy.SINGLE_INSTANCE_CACHING_SINGLE_TOP,
                        mutableSetOf()
                    ), {
                        mainActivity.activityComponent.getExploreAnimeFragment()
                    },
                    viewModelKlass = ExploreViewModel::class
                ),

                FNE(
                    R.id.exploreMangaFragment,
                    ExploreMangaFragment::class,
                    FragmentRules(
                        FragmentRules.BackstackPolicy.SINGLE_INSTANCE_CACHING_SINGLE_TOP,
                        mutableSetOf()
                    ), {
                        mainActivity.activityComponent.getExploreMangaFragment()
                    },
                    viewModelKlass = ExploreViewModel::class
                ),

                FNE(
                    R.id.profileFragment,
                    ProfileFragment::class,
                    FragmentRules(
                        FragmentRules.BackstackPolicy.SINGLE_INSTANCE_CACHING_SINGLE_TOP,
                        mutableSetOf(ExploreMangaFragment::class, ExploreAnimeFragment::class)
                    ), {
                        mainActivity.activityComponent.getProfileFragment()
                    },
                    ProfileViewModel::class
                ),

                FNE(
                    R.id.mainMediaPageFragment,
                    MainMediaPageFragment::class,
                    FragmentRules(
                        FragmentRules.BackstackPolicy.ANY,
                        mutableSetOf(
                            ExploreMangaFragment::class,
                            ExploreAnimeFragment::class,
                            ProfileFragment::class,
                            MainMediaPageFragment::class
                        )
                    ), {
                        mainActivity.activityComponent.getMainMediaPageFragment()
                    },
                    MainMediaPageViewModel::class
                )
            )
        )
    }
}