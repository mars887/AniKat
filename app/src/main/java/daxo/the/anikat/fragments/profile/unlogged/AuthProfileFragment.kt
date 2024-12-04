package daxo.the.anikat.fragments.profile.unlogged

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.AndroidEntryPoint
import daxo.the.anikat.R
import daxo.the.anikat.databinding.FragmentUnloggedProfileBinding
import daxo.the.anikat.main_activity.MainActivity
import daxo.the.navigation.simpletest.NavigationHelper
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthProfileFragment : Fragment() {

    private var _binding: FragmentUnloggedProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthProfileViewModel by viewModels()

    lateinit var navigator: NavigationHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUnloggedProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigator = (requireActivity() as MainActivity).navigationHelper

        binding.loginButton.setOnClickListener {
            launchLoginCustomTab()
        }

        lifecycleScope.launch {
            viewModel.getAuthState().collect {
                changeAuthState(it)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun changeAuthState(state: AuthProfileViewModel.AuthState) {
        with(binding.statusTextView) {
            when(state) {
                is AuthProfileViewModel.AuthState.Error -> {
                    text = "Login Error\n${state.message}"
                }
                AuthProfileViewModel.AuthState.FetchingToken -> {
                    text = "Fetching Token..."
                }
                AuthProfileViewModel.AuthState.NotStarted -> {
                    text = null
                }
                AuthProfileViewModel.AuthState.ValidatingToken -> {
                    text = "Validating Token..."
                }
                AuthProfileViewModel.AuthState.Successful -> {
                    text = "Successful"
                    navigator.navigate(R.id.profileFragment)
                }
            }
        }
    }

    private fun launchLoginCustomTab() {
        val (intent, authUri) = viewModel.createLoginCustomTab()

        intent.launchUrl(requireContext(), authUri)

        viewModel.viewModelScope.launch {
            viewModel.resetState()
            (requireActivity() as MainActivity).intentChannel.collect {
                Log.i("AUTH", "launchLoginCustomTab: $it")
                if (it != null) viewModel.handleAuthResponseIntent(it)
            }
        }
    }

    companion object {
        private const val TAG = "AuthProfileFragment"
    }
}