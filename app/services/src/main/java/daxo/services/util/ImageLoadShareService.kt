package daxo.services.util

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.core.content.FileProvider
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ActivityContext
import daxo.core.images.ImageInfoModel
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import javax.inject.Inject

class ImageLoadShareService @Inject constructor(
    @ActivityContext private val context: Context,
    private val gson: Gson
) {
    fun performLoad(imageInfo: ImageInfoModel) {
        val workerConstraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

        val workRequest = OneTimeWorkRequestBuilder<DownloadWorker>()
            .setConstraints(workerConstraints)
            .setInputData(workDataOf(IMAGE_INFO_KEY to gson.toJson(imageInfo)))
            .addTag("image saving")
            .build()

        WorkManager.getInstance(context)
            .enqueue(workRequest)
    }

    suspend fun saveImageToCache(imageInfo: ImageInfoModel): Uri? {
        return imageInfo.qualityUrl.max?.let { imageUrl ->
            val bitmap = downloadImage(imageUrl)
            val imageExtension = imageUrl.takeLastWhile { it != '.' }
            val fileName = imageUrl.replace("/","").replace(".","")

            val cachePath = File(context.cacheDir, "images")
            cachePath.mkdirs()
            val file = File(cachePath, "${fileName}.${imageExtension}")
            val fos = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            fos.close()


            FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                file
            )
        }
    }

    companion object {
        const val IMAGE_INFO_KEY = "ImageInfo"

        fun downloadImage(url: String): Bitmap {
            return BitmapFactory.decodeStream(URL(url).openConnection().getInputStream())
        }
    }
}

internal class DownloadWorker(val context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val imageInfoJson = inputData.getString(ImageLoadShareService.IMAGE_INFO_KEY) ?: return Result.failure()
        val imageInfoModel = Gson().fromJson(imageInfoJson, ImageInfoModel::class.java) ?: return Result.failure()

        return try {
            imageInfoModel.qualityUrl.max?.let { imageUrl ->
                val bitmap = ImageLoadShareService.downloadImage(imageUrl)
                saveToGallery(bitmap, imageInfoModel)
                Result.success()
            } ?: Result.failure()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    private fun saveToGallery(bitmap: Bitmap, imageInfo: ImageInfoModel) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            val contentValues = ContentValues().apply {

                put(MediaStore.Images.Media.TITLE, imageInfo.mediaTitle.handleSingleQuote())
                put(
                    MediaStore.Images.Media.DISPLAY_NAME,
                    imageInfo.mediaTitle.handleSingleQuote()
                )
                put(MediaStore.Images.Media.MIME_TYPE, "image/png")
                put(
                    MediaStore.Images.Media.DATE_ADDED,
                    System.currentTimeMillis() / 1000
                )
                put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/AniList")
            }

            val contentResolver = context.contentResolver
            val uri = contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )

            val outputStream = contentResolver.openOutputStream(uri!!)

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream!!)

            outputStream.close()
        } else {
            @Suppress("DEPRECATION")
            MediaStore.Images.Media.insertImage(
                context.contentResolver,
                bitmap,
                imageInfo.mediaTitle.handleSingleQuote(),
                ""
            )
        }
    }

    private fun String.handleSingleQuote(): String {
        return this.replace("'", "")
    }
}