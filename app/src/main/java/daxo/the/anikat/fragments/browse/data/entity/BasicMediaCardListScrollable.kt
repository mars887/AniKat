package daxo.the.anikat.fragments.browse.data.entity

import daxo.the.domain.model.media.BasicMediaCardList

data class BasicMediaCardListScrollable(
    val basicMediaCardList: BasicMediaCardList,
    var scrollPosition: Int,
)

fun BasicMediaCardList.toScrollable(scrollPosition: Int = 0): BasicMediaCardListScrollable {
    val data = BasicMediaCardListScrollable(this, 0)
    data.scrollPosition = scrollPosition
    return data
}