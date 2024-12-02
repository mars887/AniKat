package daxo.the.anikat.fragments.dialogs.imaged

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import daxo.core.images.ImageInfoModel
import daxo.the.anikat.R
import daxo.the.anikat.databinding.ImageShareLoadDialogFragmentBinding
import daxo.the.anikat.main_activity.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ImageShareLoadDialog : DialogFragment() {

    interface SelectedOptionListener {
        fun downloadSelectedImage(imageInfo: ImageInfoModel)
        fun shareSelectedImage(imageInfo: ImageInfoModel)
    }

    private var listener: SelectedOptionListener? = null

    private var _binding: ImageShareLoadDialogFragmentBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullscreenDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ImageShareLoadDialogFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageInfo = arguments?.getParcelable<ImageInfoModel>(IMAGE_INFO_KEY) ?: run {
            dismiss()
            return
        }

        with(binding) {
            root.setOnClickListener {
                dismiss()
            }

            imageView.setOnClickListener {

            }

            downloadButton.setOnClickListener {
                listener?.downloadSelectedImage(imageInfo)
            }
            shareButton.setOnClickListener {
                listener?.shareSelectedImage(imageInfo)
            }

            Glide.with(this@ImageShareLoadDialog)
                .load(imageInfo.qualityUrl.max)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>,
                        isFirstResource: Boolean
                    ): Boolean = true

                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: Target<Drawable>?,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        lifecycleScope.launch(Dispatchers.Main) {
                            imageView.setImageDrawable(resource)
                        }
                        return true
                    }

                })
                .submit()
        }
    }

    companion object {
        fun newInstance(imageInfo: ImageInfoModel, selectedOptionListener: SelectedOptionListener): ImageShareLoadDialog {
            val fragment = ImageShareLoadDialog()
            val args = Bundle().apply {
                putParcelable(IMAGE_INFO_KEY, imageInfo)
            }
            fragment.arguments = args
            fragment.listener = selectedOptionListener
            return fragment
        }

        private const val IMAGE_INFO_KEY = "imageInfo"
    }

    override fun onDetach() {
        super.onDetach()
        (requireActivity() as MainActivity).hideSystemUI()
    }
}