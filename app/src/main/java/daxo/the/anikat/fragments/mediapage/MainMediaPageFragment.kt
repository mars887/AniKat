package daxo.the.anikat.fragments.mediapage

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import daxo.the.anikat.databinding.FragmentMainMediaPageBinding
import daxo.the.domain.model.media.BasicMediaCard

@AndroidEntryPoint
class MainMediaPageFragment : Fragment() {

    private var _binding: FragmentMainMediaPageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainMediaPageBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data = arguments?.getString("BasicMediaCard") ?: return
        val card = BasicMediaCard.parseFromBundleString(data)

        binding.centeredText.text = """
                mediaId - ${card.mediaId}
                title - ${card.title}
                posterLink - ${card.coverImageEL}
                scope - ${card.averageScope}
            """.trimIndent()

        binding.titleView.text = card.title
        Glide.with(binding.root)
            .load(card.coverImageEL)
            .into(binding.topPosterImage)
    }
}