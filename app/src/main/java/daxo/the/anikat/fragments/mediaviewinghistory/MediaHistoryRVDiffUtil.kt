package daxo.the.anikat.fragments.mediaviewinghistory

import androidx.recyclerview.widget.DiffUtil
import daxo.core.model.media.media.extended.ExtendedMediaCardViewed

class MediaHistoryRVDiffUtil(val oldData: List<ExtendedMediaCardViewed>?, val newData: List<ExtendedMediaCardViewed>?) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldData?.size ?: 0

    override fun getNewListSize(): Int = newData?.size ?: 0

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldData?.get(oldItemPosition)?.mediaId == newData?.get(newItemPosition)?.mediaId


    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldData?.get(oldItemPosition)?.equals(newData?.get(newItemPosition)) ?: false
}