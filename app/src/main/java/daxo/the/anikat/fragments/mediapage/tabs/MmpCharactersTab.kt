package daxo.the.anikat.fragments.mediapage.tabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import daxo.core.model.media.media.full.FullMediaCard
import daxo.the.anikat.R
import daxo.the.anikat.databinding.FragmentMmpCharactersTabBinding
import daxo.the.anikat.fragments.mediapage.MainMediaPageFragment

class MmpCharactersTab : MmpMediaTabFragment() {

    private var _binding: FragmentMmpCharactersTabBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMmpCharactersTabBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun updateMediaInfo() {
        if (mediaCard == null) return

    }
}