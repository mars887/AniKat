package daxo.the.anikat.fragments.browse.data.entity

import daxo.core.model.media.media.extended.ExtendedMediaCardList

data class ExtendedMediaCardListScrollable(
    val extendedMediaCardList: ExtendedMediaCardList,
    var scrollPosition: Int
)

fun ExtendedMediaCardList.toScrollable(scrollPosition: Int = 0):ExtendedMediaCardListScrollable {
    return ExtendedMediaCardListScrollable(this,scrollPosition)
}