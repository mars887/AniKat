package daxo.apollo.repo.converters

import daxo.the.apollo.FilteredContentPageQuery
import daxo.the.domain.model.media.BasicMediaCard
import daxo.the.domain.model.media.enums.TitleType
import java.util.Date

object ApolloResponseConverter {
    fun toDomain(media: List<FilteredContentPageQuery.Medium?>?): List<BasicMediaCard> {
        val titleType = TitleType.ENGLISH
        val cards = mutableListOf<BasicMediaCard>()
        media?.filterNotNull()?.forEach {
            cards += BasicMediaCard(
                mediaId = it.id,
                title = getTitleBy(titleType, it.title),
                episodes = it.episodes ?: -1,
                genres = it.genres?.filterNotNull() ?: emptyList(),
                description = it.description ?: "",
                averageScope = it.averageScore ?: -1,
                favourites = it.favourites ?: 0,
                coverImageEL = it.coverImage?.extraLarge ?: it.coverImage?.large ?: "",
                lastUpdate = Date(),
                bannerImage = it.bannerImage ?: ""
            )
        }
        return cards
    }

    private fun getTitleBy(titleType: TitleType, title: FilteredContentPageQuery.Title?): String {
        if (title == null) return ""
        return when (titleType) {
            TitleType.NATIVE -> title.native ?: ""
            TitleType.ROMAJI -> title.romaji ?: title.native ?: ""
            TitleType.ENGLISH -> title.english ?: title.romaji ?: title.native ?: ""
        }
    }
}