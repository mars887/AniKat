package daxo.the.anikat.main_activity

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import daxo.the.anikat.R
import daxo.the.anikat.databinding.ActivityMainBinding
import daxo.the.domain.model.media.BasicMediaCard
import daxo.the.navigation.simpletest.NavController2Factory
import daxo.the.navigation.simpletest.NavigationHelper
import daxo.the.navigation.simpletest.toHelper
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController

    @Inject
    lateinit var navController2Factory: NavController2Factory
    private lateinit var navigationHelper: NavigationHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initBottomNavigation()
        initOnBackPressed()


        navController = getNavController()

        navigationHelper = getNavigationHelper()
    }

    private fun getNavigationHelper(): NavigationHelper =
        navController2Factory.create(
            R.id.navHostFragmentContainerView,
            BackStackNames.EXPLORE_ANIME.key,
            backstacks,
            supportFragmentManager,
            navController
        ).toHelper()

    private fun getNavController(): NavController {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragmentContainerView) as NavHostFragment
        return navHostFragment.navController
    }

    override fun onStart() {
        super.onStart()
        hideSystemUI()
    }

    private fun initOnBackPressed() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (!navigationHelper.popBackStack()) finish()
            }
        })
    }

    private fun clickToAnimeExplore() {
        navigationHelper.switchBackStack(BackStackNames.EXPLORE_ANIME.key)
    }

    private fun clickToMangaExplore() {
        navigationHelper.switchBackStack(BackStackNames.EXPLORE_MANGA.key)
    }

    private fun clickToProfile() {
        navigationHelper.switchBackStack(BackStackNames.PROFILE.key)
    }

    private fun initBottomNavigation() {
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.mainNavBarMenuExploreAnime -> {
                    clickToAnimeExplore()
                    true
                }

                R.id.mainNavBarMenuExploreManga -> {
                    clickToMangaExplore()
                    true
                }

                R.id.mainNavBarMenuProfile -> {
                    clickToProfile()
                    true
                }

                else -> false
            }
        }
    }

    private fun hideSystemUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Для Android 11 и выше
            window.insetsController?.let {
                it.hide(WindowInsets.Type.statusBars())
                it.hide(WindowInsets.Type.navigationBars())
                it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            // Для версий до Android 11
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    fun cardClicked(basicMediaCard: BasicMediaCard) {
        val bundle = Bundle()
        bundle.putString("BasicMediaCard", basicMediaCard.toBundleString())
        navigationHelper.navigate(R.id.mainMediaPageFragment, bundle)
    }

    companion object {
        private const val TAG = "MainActivity"

        private val backstacks = mapOf(
            BackStackNames.EXPLORE_ANIME.key to R.id.exploreAnimeFragment,
            BackStackNames.EXPLORE_MANGA.key to R.id.exploreMangaFragment,
            BackStackNames.PROFILE.key to R.id.profileFragment,
        )
    }

}

/*
val badge = binding.bottomNavigation.getOrCreateBadge(R.id.navigation_notifications)
badge.isVisible = true
badge.number = 99
 */