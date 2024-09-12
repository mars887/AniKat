package daxo.the.anikat.fragments.browse.data.entity

import daxo.the.anikat.type.MediaSort
import daxo.the.anikat.type.MediaType

data class ExploreMediaPageInfo(
    val pageKeys: Map<MediaType, ExploreMediaPagesInfo.MediaTypes>,
    val sort: List<MediaSort>,
    val lineName: String,
)

data class ExploreMediaPagesInfo(
    val pages: List<ExploreMediaPageInfo>
) {
    enum class MediaTypes {
        EXPLORE_ANIME_1, EXPLORE_MANGA_1,
        EXPLORE_ANIME_2, EXPLORE_MANGA_2,
        EXPLORE_ANIME_3, EXPLORE_MANGA_3,
        EMPTY
    }
}