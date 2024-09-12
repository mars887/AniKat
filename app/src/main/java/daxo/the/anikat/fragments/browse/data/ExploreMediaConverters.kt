package daxo.the.anikat.fragments.browse.data

import daxo.the.anikat.FilteredContentPageQuery
import daxo.the.anikat.fragments.browse.data.entity.MediaCardData
import daxo.the.anikat.fragments.browse.data.entity.MediaLineData
import daxo.the.anikat.core.entity.TitleType
import daxo.the.anikat.core.entity.get
import daxo.the.anikat.fragments.browse.data.entity.ExploreMediaPagesInfo
import daxo.the.anikat.type.MediaType

internal fun List<FilteredContentPageQuery.Medium?>.convertToMediaLineDataNamed(
    lineName: String,
    mediaType: MediaType,
    titleType: TitleType,
    tag: ExploreMediaPagesInfo.MediaTypes
): MediaLineData {
    val cards = mutableListOf<MediaCardData>()
    this.filterNotNull().forEach {
        cards += MediaCardData(
            mediaType = mediaType,
            mediaId = it.id,
            title = it.title?.get(titleType) ?: "",
            episodes = it.episodes.toString(),
            genres = it.genres?.filterNotNull() ?: emptyList(),
            averageScore = it.averageScore.toString(),
            favorites = it.favourites ?: 0,
            seasonYear = it.seasonYear ?: 0,
            coverImageLink = it.coverImage?.extraLarge ?: ""
        )
    }
    return MediaLineData(lineName, cards, tag)
}

