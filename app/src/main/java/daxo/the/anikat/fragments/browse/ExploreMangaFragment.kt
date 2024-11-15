package daxo.the.anikat.fragments.browse

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import daxo.the.anikat.fragments.browse.data.viewmodel.ExploreViewModel
import daxo.the.domain.model.media.enums.MediaType

@AndroidEntryPoint
class ExploreMangaFragment : ExploreFragment() {

    override val viewModel: ExploreViewModel by viewModels()

    override val mediaType: MediaType
        get() = MediaType.MANGA

    override fun onDestroy() {
        super.onDestroy()
        println("MangaF destroyed")
    }
}