package daxo.the.anikat.fragments.mediapage

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import daxo.the.anikat.databinding.FragmentMainMediaPageBinding
import daxo.core.model.media.ExtendedMediaCard
import javax.inject.Inject

@AndroidEntryPoint
class MainMediaPageFragment : Fragment() {

    private var _binding: FragmentMainMediaPageBinding? = null
    private val binding get() = _binding!!

    private lateinit var initialCard: ExtendedMediaCard

    @Inject
    lateinit var vmFactoryFactory: MainMediaPageViewModelFactoryFactory

    private val viewModel: MainMediaPageViewModel by viewModels {
        vmFactoryFactory.create(initialCard)
    }


    @Suppress("UNCHECKED_CAST")
    class MainMediaPageViewModelFactory(
        private val onMediaOpenedUseCase: OnMediaOpenedUseCase,
        private val initialMediaCard: ExtendedMediaCard
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainMediaPageViewModel(onMediaOpenedUseCase,initialMediaCard) as T
        }
    }

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

        initialCard = arguments?.getParcelable("ExtendedMediaCard")!!

        if (initialCard == null) return

        binding.centeredText.text = """
                mediaId - ${initialCard.mediaId}
                title - ${initialCard.title}
                posterLink - ${initialCard.coverImage?.large ?: initialCard.coverImage?.extraLarge}
                scope - ${initialCard.averageScore}
            """.trimIndent()

        binding.titleView.text = initialCard.title?.english
        Glide.with(binding.root)
            .load(initialCard.coverImage?.large ?: initialCard.coverImage?.extraLarge)
            .into(binding.topPosterImage)
    }

    override fun onResume() {
        super.onResume()
        viewModel.saveOpenState()
    }
}