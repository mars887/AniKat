package daxo.the.anikat.fragments.mediapage

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import daxo.the.anikat.databinding.FragmentMainMediaPageBinding
import daxo.the.anikat.fragments.browse.data.entity.MediaCardData

@AndroidEntryPoint
class MainMediaPageFragment : Fragment() {

    private var _binding: FragmentMainMediaPageBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainMediaPageBinding.inflate(layoutInflater,container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getParcelable<MediaCardData>("MediaCardData")?.let {
            binding.centeredText.text = """
                mediaId - ${it.mediaId}
                title - ${it.title}
                posterLink - ${it.coverImageLink}
                scope - ${it.averageScore}
            """.trimIndent()

            binding.titleView.text = it.title
            Glide.with(binding.root)
                .load(it.coverImageLink)
                .into(binding.topPosterImage)
        }
    }

}