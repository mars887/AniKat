package daxo.the.anikat.fragments.browse

import androidx.lifecycle.ViewModelProvider
import daxo.the.anikat.core.viewModels.ViewModelProviderFactory
import daxo.the.anikat.fragments.browse.data.viewmodel.ExploreViewModel
import daxo.the.anikat.tests.navigation_test.FragmentsNavigator
import daxo.the.anikat.type.MediaType
import javax.inject.Inject

class ExploreMangaFragment : ExploreFragment() {


    override val mediaType: MediaType
        get() = MediaType.MANGA

    override fun initViewModel(fragmentsNavigator: FragmentsNavigator) {
        viewModel = fragmentsNavigator.getVM(ExploreViewModel::class,this::class.toString())
    }
}