package daxo.the.anikat.fragments.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import dagger.hilt.android.AndroidEntryPoint
import daxo.the.anikat.R
import daxo.the.anikat.databinding.FragmentProfileBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.mainDataState
            .onEach { data ->
                if (data == null) return@onEach
                with(binding) {
                    usernameText.text = data.name
                    userAboutText.text = data.about

                    data.avatar?.let {
                        Glide.with(binding.root)
                            .load(it.medium ?: it.large)
                            .placeholder(R.drawable.rectangle_placeholder_anim_vector)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .fitCenter()
                            .into(avatarImage)
                    }

                    if(data.bannerImage != null) {
                        Glide.with(binding.root)
                            .load(data.bannerImage)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(bannerImage)
                    } else {
                        bannerImage.visibility = View.GONE
                    }
                }
            }.launchIn(lifecycleScope)

    }

    companion object {
        private const val TAG = "ProfileFragment"
    }
}