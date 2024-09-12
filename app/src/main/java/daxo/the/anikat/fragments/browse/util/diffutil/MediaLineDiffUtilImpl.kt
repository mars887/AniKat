package daxo.the.anikat.fragments.browse.util.diffutil

import androidx.recyclerview.widget.DiffUtil
import daxo.the.anikat.fragments.browse.data.entity.MediaCardData

class MediaLineDiffUtilImpl(
    private val oldList: List<MediaCardData>,
    private val newList: List<MediaCardData>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size


    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        itemsSame(oldList[oldItemPosition], newList[newItemPosition])

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        contentsSame(oldList[oldItemPosition], newList[newItemPosition])


    private fun itemsSame(old: MediaCardData, new: MediaCardData): Boolean = (old == new)

    private fun contentsSame(old: MediaCardData, new: MediaCardData): Boolean {
        return old == new
    }
}