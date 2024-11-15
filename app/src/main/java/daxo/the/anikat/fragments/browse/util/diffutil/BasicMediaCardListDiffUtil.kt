package daxo.the.anikat.fragments.browse.util.diffutil

import androidx.recyclerview.widget.DiffUtil
import daxo.the.domain.model.media.BasicMediaCardList

class BasicMediaCardListDiffUtil(val oldList: BasicMediaCardList?, val newList: BasicMediaCardList?) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList?.cards?.size ?: 0

    override fun getNewListSize(): Int = newList?.cards?.size ?: 0

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList?.cards?.get(oldItemPosition)?.mediaId == newList?.cards?.get(newItemPosition)?.mediaId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList?.cards?.get(oldItemPosition) == newList?.cards?.get(newItemPosition)
    }

}
