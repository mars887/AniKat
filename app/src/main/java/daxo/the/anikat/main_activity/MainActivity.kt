package daxo.the.anikat.main_activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import daxo.the.anikat.R
import daxo.the.anikat.databinding.ActivityMainBinding
import daxo.core.model.media.ExtendedMediaCard
import daxo.the.anikat.fragments.profile.ProfileInitializationFragment
import daxo.the.anikat.tests.StartUpFragment
import daxo.the.navigation.simpletest.NavController2Factory
import daxo.the.navigation.simpletest.NavigationHelper
import daxo.the.navigation.simpletest.toHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import kotlin.random.Random


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController

    @Inject
    lateinit var navController2Factory: NavController2Factory
    lateinit var navigationHelper: NavigationHelper

    private val _intentChannel: MutableStateFlow<Intent?> = MutableStateFlow(null)
    val intentChannel get() = _intentChannel.asStateFlow()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(null)
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

        navigationHelper = initNavigationHelper()
    }

    private fun initNavigationHelper(): NavigationHelper =
        navController2Factory.create(
            R.id.navHostFragmentContainerView,
            BackStackNames.EXPLORE_ANIME.key,
            backstacks,
            supportFragmentManager,
            navController
        ).apply {
            addFragmentToSkip(
                ProfileInitializationFragment::class.java
            )
            addFragmentsToExit(
                StartUpFragment::class.java,
                NavHostFragment::class.java
            )
        }.toHelper()

    private fun getNavController(): NavController {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragmentContainerView) as NavHostFragment
        return navHostFragment.navController
    }

    override fun onResume() {
        super.onResume()
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

                R.id.mainNavBarMenuViewHistory -> {
                    navigationHelper.switchBackStack(BackStackNames.VIEW_HISTORY.key)
                    true
                }

                else -> false
            }
        }
    }


    fun hideSystemUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Для Android 11 и выше
            window.insetsController?.hide(WindowInsets.Type.statusBars())
            window.insetsController?.hide(WindowInsets.Type.displayCutout())
            window.insetsController?.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        } else {
            // Для версий до Android 11
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility =
                (View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                        View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or
                        View.SYSTEM_UI_FLAG_LOW_PROFILE)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    fun cardClicked(mediaCard: ExtendedMediaCard) {
        val bundle = Bundle()
        bundle.putParcelable("ExtendedMediaCard", mediaCard)
        bundle.putString("key", "key")
        navigationHelper.navigate(R.id.mainMediaPageFragment, bundle)
    }

    companion object {
        private const val TAG = "MainActivity"

        private val backstacks = mapOf(
            BackStackNames.EXPLORE_ANIME.key to R.id.exploreAnimeFragment,
            BackStackNames.EXPLORE_MANGA.key to R.id.exploreMangaFragment,
            BackStackNames.PROFILE.key to R.id.profileInitializationFragment,
            BackStackNames.VIEW_HISTORY.key to R.id.mediaHistoryFragment,
        )
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        lifecycleScope.launch {
            _intentChannel.emit(intent)
        }
    }

    fun checkPermission(permission: String): Boolean {
        val result = ContextCompat.checkSelfPermission(
            this,
            permission
        )
        return result == PackageManager.PERMISSION_GRANTED
    }

    fun requestWriteExternalStoragePermission(): Flow<Pair<String, Boolean>> = channelFlow {
        val requestCode = Random.nextInt(0, Int.MAX_VALUE)
        ActivityCompat.requestPermissions(
            this@MainActivity,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            requestCode
        )

        runBlocking {
            permGrantsChannel.collect {
                if (it.first == requestCode) {
                    send(it.second)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        lifecycleScope.launch {
            permissions.forEachIndexed { index, permissions ->
                try {
                    val permissionsResult = permissions to (grantResults[index] == PackageManager.PERMISSION_GRANTED)
                    permGrantsChannel.emit(requestCode to permissionsResult)
                } catch (_: Exception) {
                }
            }
        }
    }

    fun showSnackbar(
        text: String,
        duration: Int = Snackbar.LENGTH_SHORT,
        actionTitle: String? = null,
        action: OnClickListener? = null
    ) {
        Snackbar.make(binding.root, text, duration)
            .setAnchorView(binding.bottomNavigationView).let {
                if (action != null && actionTitle != null) {
                    it.setAction(actionTitle, action)
                } else it
            }.show()
    }

    /**
     * Pair<RequestCode, Pair<Permission, isGranted>>
     */
    private val permGrantsChannel = MutableSharedFlow<Pair<Int, Pair<String, Boolean>>>()
}

/*
val badge = binding.bottomNavigation.getOrCreateBadge(R.id.navigation_notifications)
badge.isVisible = true
badge.number = 99
 */