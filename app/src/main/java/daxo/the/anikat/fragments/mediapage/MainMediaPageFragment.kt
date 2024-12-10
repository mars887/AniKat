package daxo.the.anikat.fragments.mediapage

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
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
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import daxo.core.images.ImageInfoModel
import daxo.core.model.helpers.ImageQGrades
import daxo.core.model.media.media.extended.ExtendedMediaCard
import daxo.services.ClipBoardService
import daxo.services.util.ImageLoadShareService
import daxo.the.anikat.R
import daxo.the.anikat.databinding.FragmentMainMediaPageBinding
import daxo.the.anikat.fragments.dialogs.imaged.ImageShareLoadDialog
import daxo.the.anikat.main_activity.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainMediaPageFragment : Fragment(), ImageShareLoadDialog.SelectedOptionListener {

    private var _binding: FragmentMainMediaPageBinding? = null
    private val binding get() = _binding!!

    private lateinit var initialCard: ExtendedMediaCard

    @Inject
    lateinit var vmFactoryFactory: MainMediaPageViewModelFactoryFactory

    @Inject
    lateinit var imageService: ImageLoadShareService

    @Inject
    lateinit var clipBoardService: ClipBoardService

    private val viewModel: MainMediaPageViewModel by viewModels {
        vmFactoryFactory.create(initialCard)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainMediaPageBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialCard = arguments?.getParcelable("ExtendedMediaCard") ?: return

        binding.centeredText.text = """
                mediaId - ${initialCard.mediaId}
                title - ${initialCard.title}
                posterLink - $mediaCoverImage
                scope - ${initialCard.averageScore}
            """.trimIndent()

        binding.titleView.text = initialCard.title?.english
        Glide.with(binding.root)
            .load(mediaCoverImage)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.topPosterImage)

        mediaBannerImage?.let {
            Glide.with(binding.root)
                .load(mediaBannerImage)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.topBannerImage)
        } ?: run {
            binding.root.jumpToState(R.id.hideBanner)
        }


        binding.topPosterImage.setOnLongClickListener {                                 // poster long click
            initialCard.coverImage?.let {
                viewModel.openImageLongClickMenu(
                    this, ImageInfoModel(
                        initialCard.title?.userPreferred ?: initialCard.title?.native ?: "",
                        mediaCoverImage,
                        ImageQGrades(initialCard.coverImage!!),
                        initialCard.mediaId
                    )
                )
                true
            } ?: false
        }

        binding.topBannerImage.setOnLongClickListener {                                 // banner long click
            initialCard.bannerImage?.let {
                viewModel.openImageLongClickMenu(
                    this, ImageInfoModel(
                        initialCard.title?.userPreferred ?: initialCard.title?.native ?: "",
                        mediaBannerImage,
                        ImageQGrades(mediaBannerImage!!),
                        initialCard.mediaId
                    )
                )
                true
            } ?: false
        }

        binding.titleView.setOnLongClickListener {
            mediaTitleText?.let { userPreferred ->
                clipBoardService.pasteToClipboard("title", userPreferred)
                (requireActivity() as MainActivity).showSnackbar("title copied")
                true
            } ?: false
        }
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