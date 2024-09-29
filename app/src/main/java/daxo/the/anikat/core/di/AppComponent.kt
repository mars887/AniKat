package daxo.the.anikat.core.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import daxo.the.anikat.App
import daxo.the.anikat.core.di.modules.ApolloModule
import daxo.the.anikat.core.di.modules.AppModule
import daxo.the.anikat.core.di.modules.ViewModelFactoryModule
import daxo.the.anikat.fragments.browse.ExploreAnimeFragment
import daxo.the.anikat.fragments.browse.ExploreMangaFragment
import daxo.the.anikat.fragments.profile.ProfileFragment
import daxo.the.anikat.main_activity.MainActivity
import daxo.the.anikat.main_activity.di.ActivityComponent
import daxo.the.anikat.main_activity.di.FragmentsNavigatorModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ViewModelFactoryModule::class,
        AppModule::class,
        ApolloModule::class,
    ], dependencies = [

    ]
)
interface AppComponent : AndroidInjector<App> {

    fun activityComponent(
        fragmentsNavigatorModule: FragmentsNavigatorModule
    ): ActivityComponent


    @Component.Builder
    interface Builder {
        @BindsInstance
        fun withContext(context: Context): Builder

        fun build(): AppComponent
    }
}