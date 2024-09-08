package daxo.the.anikat.main_activity.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import daxo.the.anikat.core.viewModels.ViewModelKey
import daxo.the.anikat.fragments.browse.data.viewmodel.ExploreViewModel
import daxo.the.anikat.fragments.mediapage.MainMediaPageViewModel
import daxo.the.anikat.fragments.profile.ProfileViewModel

@Module
interface ViewModelBindsModule {

    @Binds
    @IntoMap
    @ViewModelKey(ExploreViewModel::class)
    abstract fun bindExploreViewModel(viewModel: ExploreViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun bindProfileViewModel(viewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainMediaPageViewModel::class)
    abstract fun bindMainMediaPageViewModel(viewModel: MainMediaPageViewModel): ViewModel
}