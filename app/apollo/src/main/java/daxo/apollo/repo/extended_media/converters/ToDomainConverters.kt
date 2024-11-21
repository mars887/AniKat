package daxo.apollo.repo.extended_media.converters

import daxo.the.apollo.GetMediaExtendedCardFilteredQuery
import daxo.core.model.media.minis.*

internal fun GetMediaExtendedCardFilteredQuery.Title.toDomain(): MediaTitle {
    return MediaTitle(userPreferred, romaji, english, native)
}

internal fun GetMediaExtendedCardFilteredQuery.Studios.toDomain(): Studios {
    return Studios(
        this.edges?.map { Studios.Node(it?.isMain, it?.node?.id, it?.node?.name) }
    )
}

internal fun GetMediaExtendedCardFilteredQuery.NextAiringEpisode.toDomain(): NextAiringEpisode {
    return NextAiringEpisode(airingAt, timeUntilAiring, episode)
}

internal fun GetMediaExtendedCardFilteredQuery.CoverImage.toDomain(): CoverImage {
    return CoverImage(extraLarge,large)
}