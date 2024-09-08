package daxo.the.anikat.core.entity

import daxo.the.anikat.FilteredContentPageQuery

enum class TitleType {
    NATIVE, ROMAJI, ENGLISH
}

fun FilteredContentPageQuery.Title.get(type: TitleType): String? = when (type) {
    TitleType.NATIVE -> native
    TitleType.ROMAJI -> romaji
    TitleType.ENGLISH -> english
}
