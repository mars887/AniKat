package daxo.the.anikat.fragments.mediapage.tabs

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import daxo.core.model.media.media.full.FullMediaCard
import daxo.the.anikat.fragments.mediapage.MainMediaPageFragment

abstract class MmpMediaTabFragment : Fragment() {

    abstract fun updateMediaInfo()

    open fun applyMediaCard(card: FullMediaCard) {
        mediaCard = card
    }

    protected var mediaCard: FullMediaCard? = null
        set(value) {
            field = value
            updateMediaInfo()
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (mediaCard == null) (parentFragment as? MainMediaPageFragment)?.let { parent ->
            mediaCard = parent.requireFullMediaCard()
        }
        updateMediaInfo()
    }

}