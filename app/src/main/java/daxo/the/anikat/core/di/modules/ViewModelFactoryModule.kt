package daxo.the.anikat.core.di.modules

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import daxo.the.anikat.core.viewModels.ViewModelProviderFactory

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(modelProviderFactory: ViewModelProviderFactory): ViewModelProvider.Factory
}