package daxo.the.anikat.main_activity.di

import dagger.Module
import dagger.Provides
import daxo.the.anikat.fragments.browse.data.viewmodel.ExploreViewModel
import daxo.the.anikat.fragments.browse.usecases.GetMediaBoardsUseCase
import daxo.the.anikat.fragments.mediapage.MainMediaPageViewModel
import daxo.the.anikat.fragments.profile.ProfileViewModel

@Module
class ViewModelProvidersModule {

    @Provides
    fun provideExploreViewModel(getMediaBoardsUseCase: GetMediaBoardsUseCase): ExploreViewModel {
        return ExploreViewModel(getMediaBoardsUseCase = getMediaBoardsUseCase)
    }

    @Provides
    fun provideProfileViewModel(): ProfileViewModel {
        return ProfileViewModel()
    }

    @Provides
    fun provideMainMediaPageViewModel(): MainMediaPageViewModel {
        return MainMediaPageViewModel()
    }


}