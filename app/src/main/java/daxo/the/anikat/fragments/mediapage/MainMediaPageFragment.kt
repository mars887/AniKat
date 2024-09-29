package daxo.the.anikat.fragments.mediapage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import daxo.the.anikat.R
import daxo.the.anikat.databinding.FragmentMainMediaPageBinding
import daxo.the.anikat.fragments.browse.data.entity.MediaCardData

class MainMediaPageFragment(
    private val viewModel: MainMediaPageViewModel
) : Fragment() {

    private var _binding: FragmentMainMediaPageBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainMediaPageBinding.inflate(layoutInflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getParcelable<MediaCardData>("MediaCardData")?.let {
            binding.centeredText.text = """
                mediaId - ${it.mediaId}
                title - ${it.title}
                posterLink - ${it.coverImageLink}
                scope - ${it.averageScore}
            """.trimIndent()
        }
    }

}