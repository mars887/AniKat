package daxo.the.anikat.fragments.browse.util.diffutil

import androidx.recyclerview.widget.DiffUtil
import daxo.the.anikat.fragments.browse.data.entity.ExtendedMediaCardListScrollable

class ExploreMediaDiffUtilImpl(
    private val oldList: List<ExtendedMediaCardListScrollable>,
    private val newList: List<ExtendedMediaCardListScrollable>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].extendedMediaCardList.listName == newList[newItemPosition].extendedMediaCardList.listName
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].extendedMediaCardList.cards == newList[newItemPosition].extendedMediaCardList.cards
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any {
        val oldElement = oldList[oldItemPosition]
        val newElement = newList[newItemPosition]

        val changes = StringBuilder()
        if(oldElement.extendedMediaCardList.cards != newElement.extendedMediaCardList.cards) changes.append("cards")
        if(oldElement.extendedMediaCardList.listName != newElement.extendedMediaCardList.listName) changes.append("lineName")
        if(oldElement.extendedMediaCardList.requestData != newElement.extendedMediaCardList.requestData) changes.append("requestData")
        return changes.toString()
    }
}