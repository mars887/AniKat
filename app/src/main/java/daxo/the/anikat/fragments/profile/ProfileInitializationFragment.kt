package daxo.the.anikat.fragments.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import daxo.core.api.ITokenRepo
import daxo.the.anikat.R
import daxo.the.anikat.main_activity.MainActivity
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class ProfileInitializationFragment : Fragment() {

    @Inject
    lateinit var tokenRepo: ITokenRepo

    private lateinit var tokenRequestResult: Deferred<Boolean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tokenRequestResult = lifecycleScope.async {
            tokenRepo.tokenAvailable()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_profile_initialization, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            val tokenAvailable = tokenRequestResult.await()

            val navigator = (requireActivity() as MainActivity).navigationHelper

            if(tokenAvailable) {
                navigator.navigate(R.id.profileFragment)
            } else {
                navigator.navigate(R.id.authProfileFragment)
            }
            Log.i(TAG, "onViewCreated: navigating $tokenAvailable")
        }
    }

    companion object {
        private const val TAG = "ProfileInitializationFr"
    }
}