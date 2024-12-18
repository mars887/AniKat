package daxo.the.anikat.fragments.mediapage

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.toColor
import androidx.core.graphics.toColorInt
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import daxo.core.images.ImageInfoModel
import daxo.core.model.helpers.ImageQGrades
import daxo.core.model.media.media.extended.ExtendedMediaCard
import daxo.core.model.media.media.full.FullMediaCard
import daxo.services.ClipBoardService
import daxo.services.util.ImageLoadShareService
import daxo.the.anikat.R
import daxo.the.anikat.databinding.FragmentMmpMainMediaPageBinding
import daxo.the.anikat.fragments.dialogs.imaged.ImageShareLoadDialog
import daxo.the.anikat.fragments.mediapage.tabs.*
import daxo.the.anikat.fragments.mediapage.tabslayout.ITabsLayoutHelper
import daxo.the.anikat.fragments.mediapage.tabslayout.ITabsLayoutHelper.*
import daxo.the.anikat.fragments.mediapage.tabslayout.TabsLayoutHelperFactoryV2
import daxo.the.anikat.fragments.mediapage.tabslayout.TabsLayoutHelperV2
import daxo.the.anikat.main_activity.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.reflect.KClass

@AndroidEntryPoint
class MainMediaPageFragment : Fragment(), ImageShareLoadDialog.SelectedOptionListener {

    private var _binding: FragmentMmpMainMediaPageBinding? = null
    private val binding get() = _binding!!

    private lateinit var initialCard: ExtendedMediaCard

    @Inject
    lateinit var vmFactoryFactory: MainMediaPageViewModelFactoryFactory

    @Inject
    lateinit var tabsLayoutHelperFactory: TabsLayoutHelperFactoryV2
    private lateinit var tabsLayoutHelper: ITabsLayoutHelper

    @Inject
    lateinit var imageService: ImageLoadShareService

    @Inject
    lateinit var clipBoardService: ClipBoardService

    private val viewModel: MainMediaPageViewModel by viewModels {
        vmFactoryFactory.create(initialCard)
    }

    private var currentFragment: Fragment? = null
    private val tabsFragments: MutableMap<KClass<out Fragment>, Fragment> = mutableMapOf()

    private var fullMediaCard: FullMediaCard? = null
        set(value) {
            field = value
            fullMediaCardInitialized()
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMmpMainMediaPageBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialCard = arguments?.getParcelable("ExtendedMediaCard") ?: return

        /* studio and season year text init */
        binding.studioFavoritesYearView.text = buildString {
            initialCard.studios?.studios?.find { it.isMain ?: false }?.studio?.let { studio ->
                append(studio.name)
            }
            append(" • ")
            initialCard.season?.rawValue?.lowercase()?.replaceFirstChar { it.uppercase() }?.let {
                append(it)
                append(" ")
            }
            initialCard.seasonYear?.let {
                append(it)
            }
        }

        /* title init */
        binding.titleView.text = mediaTitleText

        /* poster image init */
        Glide.with(binding.root)
            .load(mediaCoverImage)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.topPosterImage)

        /* banner image init */
        mediaBannerImage?.let {
            Glide.with(binding.root)
                .load(mediaBannerImage)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.topBannerImage)
        } ?: run {
            Glide.with(binding.root)
                .load(mediaCoverImage)
                .transition(DrawableTransitionOptions.withCrossFade())
                .centerCrop()
                .into(binding.topBannerImage)
            //binding.root.jumpToState(R.id.hideBanner)
        }

//        binding.topPosterImage.setOnClickListener {
//            Blurry.with(requireContext())
//                .radius(25)
//                .sampling(4)
//                .capture(binding.topBannerImage)
//                .into(binding.topBannerImage)
//        }


        imagesDialogSetup()

        binding.titleView.setOnLongClickListener {
            mediaTitleText?.let { userPreferred ->
                clipBoardService.pasteToClipboard("title", userPreferred)
                (requireActivity() as MainActivity).showSnackbar("title copied")
                true
            } ?: false
        }


        tabsLayoutHelper = tabsLayoutHelperFactory.create(
            listOf(
                Triple(binding.pagesTabsLayout.overviewTab, Tabs.OVERVIEW.title, Tabs.OVERVIEW.id),
                Triple(binding.pagesTabsLayout.watchTab, Tabs.WATCH.title, Tabs.WATCH.id),
                Triple(binding.pagesTabsLayout.charactersTab, Tabs.CHARACTERS.title, Tabs.CHARACTERS.id),
                Triple(binding.pagesTabsLayout.staffTab, Tabs.STAFF.title, Tabs.STAFF.id),
                Triple(binding.pagesTabsLayout.reviewsTab, Tabs.REVIEWS.title, Tabs.REVIEWS.id),
                Triple(binding.pagesTabsLayout.statsTab, Tabs.STATS.title, Tabs.STATS.id),
                Triple(binding.pagesTabsLayout.socialTab, Tabs.SOCIAL.title, Tabs.SOCIAL.id),
            ),
            lifecycleScope
        )
        (tabsLayoutHelper as? TabsLayoutHelperV2)?.underlineView = binding.pagesTabsLayout.underline
        tabsLayoutHelper.activeColor = Color.parseColor(initialCard.coverImage?.color ?: "Red").toColor()


        // tabs

        initialCard.coverImage?.color?.let {
            tabsLayoutHelper.inactiveColor = Color.parseColor(it).toColor()
        }

        setupFragmentContainer()
        tabsLayoutHelper.setupClickListener()
    }

    fun FragmentTransaction.commmit(klass: KClass<out Fragment>, direction: Int) {

        val inAnim = if (direction < 0) R.anim.mmp_tabs_left_in_anim else R.anim.mmp_tabs_right_in_anim
        val outAnim = if (direction < 0) R.anim.mmp_tabs_right_out_anim else R.anim.mmp_tabs_left_out_anim

        setCustomAnimations(inAnim, outAnim, inAnim, outAnim)

        currentFragment?.let {
            detach(it)
        }
        currentFragment = if (tabsFragments[klass] == null) {
            val fragment = klass.java.getDeclaredConstructor().newInstance()
            add(R.id.mmpFragmentsContainer, fragment)
            tabsFragments[klass] = fragment
            fragment
        } else {
            val fragment = tabsFragments[klass]
            attach(fragment!!)
            fragment
        }
    }

    private fun ITabsLayoutHelper.setupClickListener() {
        clickListener = ClickListener { id, view, direction ->
            val klass = Tabs.entries.find { it.id == id }?.klass?.kotlin ?: return@ClickListener

            when (id) {
                Tabs.OVERVIEW.id -> {
                    childFragmentManager.commit {
                        commmit(klass, direction)
                    }
                }

                Tabs.WATCH.id -> {
                    childFragmentManager.commit {
                        commmit(klass, direction)
                    }
                }

                Tabs.CHARACTERS.id -> {
                    childFragmentManager.commit {
                        commmit(klass, direction)
                    }
                }

                Tabs.STAFF.id -> {

                }

                Tabs.REVIEWS.id -> {

                }

                Tabs.STATS.id -> {

                }

                Tabs.SOCIAL.id -> {

                }
            }
        }
    }

    fun requireFullMediaCard(): FullMediaCard? {
        if (fullMediaCard == null) {
            lifecycleScope.launch {
                viewModel.requireFullMediaCard().collect {
                    fullMediaCard = it
                }
            }
        }
        return fullMediaCard
    }

    private fun fullMediaCardInitialized() {
        fullMediaCard?.let {
            (childFragmentManager.fragments.lastOrNull() as? MmpMediaTabFragment)?.applyMediaCard(it)
        }
    }

    private fun setupFragmentContainer() {
        childFragmentManager.fragments.forEach {
            tabsFragments[it::class] = it
        }

        childFragmentManager.fragments.lastOrNull()?.let { fragment ->
            currentFragment = fragment
            Tabs.entries.find { it.klass == fragment.javaClass }?.id?.let {
                tabsLayoutHelper.instantSwitchTo(it)
            }
        } ?: run {
            childFragmentManager.commit {
                currentFragment = MmpOverviewTab()
                tabsFragments[currentFragment!!::class] = currentFragment!!
                add(R.id.mmpFragmentsContainer, currentFragment!!)
            }
        }
    }

    private fun imagesDialogSetup() {
        binding.topPosterImage.setOnLongClickListener {                                 // poster long click
            initialCard.coverImage?.let {
                imageLongClickSetup(mediaTitleText ?: "", ImageQGrades(initialCard.coverImage!!), mediaCoverImage)
                true
            } ?: false
        }

        binding.topBannerImage.setOnLongClickListener {                                 // banner long click
            initialCard.bannerImage?.let {
                imageLongClickSetup(mediaTitleText ?: "", ImageQGrades(mediaBannerImage!!), mediaBannerImage)
                true
            } ?: false
        }
    }

    private fun imageLongClickSetup(imageTitle: String, imageQGrades: ImageQGrades, preloadedImage: String?) {
        viewModel.openImageLongClickMenu(
            this, ImageInfoModel(
                imageTitle,
                preloadedImage,
                imageQGrades,
                initialCard.mediaId
            )
        )
    }

    private val mediaCoverImage by lazy { initialCard.coverImage?.large ?: initialCard.coverImage?.extraLarge }
    private val mediaBannerImage by lazy { initialCard.bannerImage }
    private val mediaTitleText by lazy { initialCard.title?.userPreferred ?: initialCard.title?.native }

    override fun onResume() {
        super.onResume()
        viewModel.saveOpenState()
    }

    override fun downloadSelectedImage(imageInfo: ImageInfoModel) {
        Log.i(TAG, "downloadSelectedImage: $imageInfo")
        lifecycleScope.launch(Dispatchers.IO) {
            if (!imageService.checkPermissions()) {
                imageService.performLoad(imageInfo)
            } else {
                (requireActivity() as MainActivity).requestWriteExternalStoragePermission().collect {
                    if (it.first == Manifest.permission.WRITE_EXTERNAL_STORAGE && it.second) {
                        downloadSelectedImage(imageInfo)
                    } else {
                        (requireActivity() as MainActivity).showSnackbar(
                            "loading failed",
                            Snackbar.LENGTH_LONG,
                            "try again"
                        ) {
                            downloadSelectedImage(imageInfo)
                        }
                    }
                }
            }
        }
    }

    override fun shareSelectedImage(imageInfo: ImageInfoModel) {
        Log.i(TAG, "shareSelectedImage: $imageInfo")
        lifecycleScope.launch(Dispatchers.IO) {
            val uri = imageService.saveImageToCache(imageInfo)

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "image/*"
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            startActivity(Intent.createChooser(shareIntent, "Share Image"))
        }
    }

    companion object {
        private const val TAG = "MainMediaPageFragment"
    }
}