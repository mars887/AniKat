package daxo.the.anikat.fragments.mediaviewinghistory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import daxo.the.anikat.R
import daxo.the.anikat.databinding.FragmentMediaHistoryBinding
import daxo.the.anikat.databinding.FragmentProfileBinding

@AndroidEntryPoint
class MediaHistoryFragment : Fragment() {

    private var _binding: FragmentMediaHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMediaHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

}