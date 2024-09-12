package daxo.the.anikat.fragments.browse.util.diffutil

import androidx.recyclerview.widget.DiffUtil
import daxo.the.anikat.fragments.browse.data.entity.MediaLineData

class ExploreMediaDiffUtilImpl(
    private val oldList: List<MediaLineData>,
    private val newList: List<MediaLineData>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        itemsSame(oldList[oldItemPosition], newList[newItemPosition])

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        contentsSame(oldList[oldItemPosition], newList[newItemPosition])


    private fun itemsSame(old: MediaLineData, new: MediaLineData): Boolean = (old === new)

    private fun contentsSame(old: MediaLineData, new: MediaLineData): Boolean {
        return old == new
    }
}