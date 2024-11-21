package daxo.the.anikat.fragments.browse.data.objects

import daxo.core.model.media.enums.MediaSort
import daxo.core.model.media.enums.MediaType

object ExploreCardListsInfo {
    val info = mapOf<MediaType,List<ExploreCardListInfo>>(
        MediaType.ANIME to listOf(
            ExploreCardListInfo(
                sort = listOf(MediaSort.TRENDING_DESC, MediaSort.POPULARITY_DESC),
                lineName = "Trending now",
                lineTag = "ExploreAnime_Sort_TRENDING_DESC_and_POPULARITY_DESC"
            ),
            ExploreCardListInfo(
                sort = listOf(MediaSort.POPULARITY_DESC),
                lineName = "Popular all time",
                lineTag = "ExploreAnime_Sort_POPULARITY_DESC"
            ),
            ExploreCardListInfo(
                sort = listOf(MediaSort.SCORE_DESC),
                lineName = "Top 100",
                lineTag = "ExploreAnime_Sort_SCORE_DESC"
            )
        ),
        MediaType.MANGA to listOf(
            ExploreCardListInfo(
                sort = listOf(MediaSort.TRENDING_DESC, MediaSort.POPULARITY_DESC),
                lineName = "Trending now",
                lineTag = "ExploreManga_Sort_TRENDING_DESC_and_POPULARITY_DESC"
            ),
            ExploreCardListInfo(
                sort = listOf(MediaSort.POPULARITY_DESC),
                lineName = "Popular all time",
                lineTag = "ExploreManga_Sort_POPULARITY_DESC"
            ),
            ExploreCardListInfo(
                sort = listOf(MediaSort.SCORE_DESC),
                lineName = "Top 100",
                lineTag = "ExploreManga_Sort_SCORE_DESC"
            )
        )
    )

    data class ExploreCardListInfo(
        val sort: List<MediaSort>,
        val lineName: String,
        val lineTag: String
    )
}