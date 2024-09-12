package daxo.the.anikat.main_activity.di

import androidx.fragment.app.FragmentManager
import dagger.BindsInstance
import dagger.Subcomponent
import daxo.the.anikat.core.di.scopes.ActivityScope
import daxo.the.anikat.fragments.browse.ExploreAnimeFragment
import daxo.the.anikat.fragments.browse.ExploreFragment
import daxo.the.anikat.fragments.browse.ExploreMangaFragment
import daxo.the.anikat.fragments.browse.data.viewmodel.ExploreViewModel
import daxo.the.anikat.fragments.browse.di.ExploreModule
import daxo.the.anikat.fragments.mediapage.MainMediaPageFragment
import daxo.the.anikat.fragments.mediapage.MainMediaPageViewModel
import daxo.the.anikat.fragments.profile.ProfileFragment
import daxo.the.anikat.fragments.profile.ProfileModule
import daxo.the.anikat.fragments.profile.ProfileViewModel
import daxo.the.anikat.main_activity.MainActivity

@ActivityScope
@Subcomponent(
    modules = [
        FragmentsNavigatorModule::class,
        FragmentsModule::class,
        ViewModelProvidersModule::class,
        ViewModelBindsModule::class,
        ExploreModule::class
    ]
)
interface ActivityComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(profileFragment: ProfileFragment)
    fun inject(exploreFragment: ExploreFragment)

    fun getProfileFragment(): ProfileFragment
    fun getExploreAnimeFragment(): ExploreAnimeFragment
    fun getExploreMangaFragment(): ExploreMangaFragment
    fun getMainMediaPageFragment(): MainMediaPageFragment

    fun getProfileViewModel(): ProfileViewModel
    fun getExploreViewModel(): ExploreViewModel
    fun getMainMediaPageViewModel(): MainMediaPageViewModel
}