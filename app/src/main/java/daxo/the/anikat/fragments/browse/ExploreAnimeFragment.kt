package daxo.the.anikat.fragments.browse

import androidx.lifecycle.ViewModelProvider
import daxo.the.anikat.core.viewModels.ViewModelProviderFactory
import daxo.the.anikat.fragments.browse.data.viewmodel.ExploreViewModel
import daxo.the.anikat.tests.navigation_test.FragmentsNavigator
import daxo.the.anikat.type.MediaType
import javax.inject.Inject

class ExploreAnimeFragment(
    viewModel: ExploreViewModel,fragmentsNavigator: FragmentsNavigator
) : ExploreFragment(viewModel,fragmentsNavigator) {

    override val mediaType: MediaType
        get() = MediaType.ANIME

}