package daxo.the.anikat.fragments.mediapage.tabs

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import daxo.the.anikat.R
import daxo.the.anikat.databinding.FragmentMmpCharactersTabBinding
import daxo.the.anikat.databinding.FragmentMmpOverviewTabBinding

class MmpOverviewTab : MmpMediaTabFragment() {

    private var _binding: FragmentMmpOverviewTabBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMmpOverviewTabBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun updateMediaInfo() {
        Log.i(TAG, "updateMediaInfo: ${mediaCard?.id}")
        mediaCard?.favourites?.let {
            binding.favoritesInfoLayoutText.text = it.toString()
        }

        mediaCard?.popularity?.let {
            binding.popularityInfoLayoutText.text = it.toString()
        }

        mediaCard?.averageScore?.let {
            binding.scoreInfoLayoutText.text = "$it%"
        }
    }

    companion object {
        private const val TAG = "MmpOverviewTab"
    }
}