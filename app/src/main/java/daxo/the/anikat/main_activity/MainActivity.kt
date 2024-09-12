package daxo.the.anikat.main_activity

import android.os.Build
import android.os.Bundle
import android.util.Log
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
import daxo.the.anikat.App
import daxo.the.anikat.R
import daxo.the.anikat.databinding.ActivityMainBinding
import daxo.the.anikat.main_activity.di.ActivityComponent
import daxo.the.anikat.main_activity.di.FragmentsNavigatorModule
import daxo.the.anikat.tests.navigation_test.FragmentsNavigator
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController

    private val TAG = "MainActivity"

    lateinit var activityComponent: ActivityComponent

    @Inject
    lateinit var fragmentsNavigator: FragmentsNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activityComponent = App.instance.appComponent.activityComponent(
            FragmentsNavigatorModule(
                supportFragmentManager,
                this
            )
        )
        activityComponent.inject(this)

        initNavController()

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initBottomNavigation()
        initOnBackPressed()

        fragmentsNavigator.navigateTo(R.id.exploreAnimeFragment)
    }

    override fun onStart() {
        super.onStart()
        hideSystemUI()
    }

    private fun initNavController() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
    }


    private fun initOnBackPressed() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                println("back pressed")
                //navController.popBackStack()

                if (fragmentsNavigator.onBackPressed()) {
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }

    private fun clickToAnimeExplore() {
//        val navOptions = NavOptions.Builder().setLaunchSingleTop(true).build()
//        navController.navigate(R.id.exploreAnimeFragment, null, navOptions)

        fragmentsNavigator.navigateTo(R.id.exploreAnimeFragment)
    }

    private fun clickToMangaExplore() {
//        val navOptions = NavOptions.Builder().setLaunchSingleTop(true).build()
//        navController.navigate(R.id.exploreMangaFragment, null, navOptions)

        fragmentsNavigator.navigateTo(R.id.exploreMangaFragment)
    }

    private fun clickToProfile() {
        fragmentsNavigator.navigateTo(R.id.profileFragment)
    }

    private fun initBottomNavigation() {
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.mainNavBarMenuExploreAnime -> {

                    clickToAnimeExplore()

                    Log.d(TAG, "initBottomNavigation: clicked explore anime")
                    true
                }

                R.id.mainNavBarMenuExploreManga -> {

                    clickToMangaExplore()

                    Log.d(TAG, "initBottomNavigation: clicked explore manga")
                    true
                }

                R.id.mainNavBarMenuProfile -> {

                    clickToProfile()

                    Log.d(TAG, "initBottomNavigation: clicked profile")
                    true
                }

                else -> {
                    Log.d(TAG, "initBottomNavigation: clicked unexpected")
                    false
                }
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

}

/*
val badge = binding.bottomNavigation.getOrCreateBadge(R.id.navigation_notifications)
badge.isVisible = true
badge.number = 99
 */