package daxo.the.anikat.fragments.browse.util.diffutil

import androidx.recyclerview.widget.DiffUtil
import daxo.the.anikat.fragments.browse.data.entity.BasicMediaCardListScrollable

class ExploreMediaDiffUtilImpl(
    private val oldList: List<BasicMediaCardListScrollable>,
    private val newList: List<BasicMediaCardListScrollable>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].basicMediaCardList.listName == newList[newItemPosition].basicMediaCardList.listName
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].basicMediaCardList.cards == newList[newItemPosition].basicMediaCardList.cards
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any {
        val oldElement = oldList[oldItemPosition]
        val newElement = newList[newItemPosition]

        val changes = StringBuilder()
        if(oldElement.basicMediaCardList.cards != newElement.basicMediaCardList.cards) changes.append("cards")
        if(oldElement.basicMediaCardList.listName != newElement.basicMediaCardList.listName) changes.append("lineName")
        if(oldElement.basicMediaCardList.requestData != newElement.basicMediaCardList.requestData) changes.append("requestData")
        return changes.toString()
    }
}