package daxo.the.anikat.fragments.browse

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import daxo.the.anikat.fragments.browse.data.viewmodel.ExploreViewModel
import daxo.core.model.media.enums.MediaType

@AndroidEntryPoint
class ExploreAnimeFragment : ExploreFragment() {

    override val viewModel: ExploreViewModel by viewModels()

    override val mediaType: MediaType
        get() = MediaType.ANIME

    override fun onDestroy() {
        super.onDestroy()
        println("AnimeF destroyed")
    }
}