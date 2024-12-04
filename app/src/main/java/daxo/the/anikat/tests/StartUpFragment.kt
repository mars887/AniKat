package daxo.the.anikat.tests

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import daxo.apollo.auth.CheckTokenService
import daxo.the.anikat.R
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class StartUpFragment : Fragment() {

    @Inject
    lateinit var tokenValidator: CheckTokenService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_start_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            tokenValidator.checkToken(true)
        }
    }
}