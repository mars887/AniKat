package daxo.the.anikat.fragments.mediapage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import daxo.the.anikat.R

class MainMediaPageFragment(
    private val viewModel: MainMediaPageViewModel
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_media_page, container, false)
    }

}