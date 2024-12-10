package daxo.apollo.repo.converters


import daxo.apollo.repo.converters.enums.ExternalLinkTypeConverter.toDomain
import daxo.apollo.repo.converters.enums.MediaFormatConverter.toDomain
import daxo.apollo.repo.converters.enums.MediaListStatusConverter.toDomain
import daxo.apollo.repo.converters.enums.MediaRankConverter.toDomain
import daxo.apollo.repo.converters.enums.MediaRelationTypeConverter.toDomain
import daxo.apollo.repo.converters.enums.MediaSeasonConverter.toDomain
import daxo.the.apollo.fragment.MediaStreamingEpisode as ApolloMediaStreamingEpisode
import daxo.core.model.byApollo.media.MediaStreamingEpisode as DomainMediaStreamingEpisode
import daxo.the.apollo.fragment.BasicStats as ApolloBasicStats
import daxo.core.model.byApollo.media.BasicStats as DomainBasicStats
import daxo.the.apollo.fragment.MediaTrailer as ApolloMediaTrailer
import daxo.core.model.byApollo.media.MediaTrailer as DomainMediaTrailer
import daxo.the.apollo.fragment.MediaTag as ApolloMediaTag
import daxo.core.model.byApollo.media.MediaTag as DomainMediaTag
import daxo.the.apollo.fragment.BasicMediaRelationQuery as ApolloBasicMediaRelationQuery
import daxo.the.apollo.fragment.MediaRanking as ApolloMediaRanking
import daxo.core.model.byApollo.media.MediaRanking as DomainMediaRanking
import daxo.the.apollo.fragment.MediaExternalLink as ApolloMediaExternalLink
import daxo.core.model.byApollo.media.MediaExternalLink as DomainMediaExternalLink
import daxo.the.apollo.fragment.MediaCoverImage as ApolloMediaCoverImage
import daxo.core.model.byApollo.media.MediaCoverImage as DomainMediaCoverImage
import daxo.the.apollo.fragment.MediaTitle as ApolloMediaTitle
import daxo.core.model.byApollo.media.MediaTitle as DomainMediaTitle
import daxo.core.model.byApollo.media.BasicMedia as DomainBasicMedia
import daxo.core.model.byApollo.media.BasicMediaRelation as DomainBasicMediaRelation
import daxo.the.apollo.fragment.BasicMedia as ApolloBasicMedia

object MediaCommonConverter {
    fun ApolloBasicMedia.toDomain(): DomainBasicMedia {
        return DomainBasicMedia(id, title?.mediaTitle?.toDomain(), coverImage?.mediaCoverImage?.toDomain())
    }

    fun ApolloMediaTitle.toDomain(): DomainMediaTitle {
        return DomainMediaTitle(userPreferred, romaji, english, native)
    }

    fun ApolloMediaCoverImage.toDomain(): DomainMediaCoverImage {
        return DomainMediaCoverImage(extraLarge, large, medium, color)
    }

    fun ApolloMediaExternalLink.toDomain(): DomainMediaExternalLink {
        return DomainMediaExternalLink(id, url, site, siteId, type?.toDomain(), language, color, icon, notes, isDisabled)
    }

    fun ApolloMediaRanking.toDomain(): DomainMediaRanking {
        return DomainMediaRanking(id, rank, type.toDomain(), format.toDomain(), year, season?.toDomain(), allTime, context)
    }

    fun ApolloBasicMediaRelationQuery.toDomain(): List<DomainBasicMediaRelation>? {
        if (edges == null) return null
        return edges.map { DomainBasicMediaRelation(it?.id, it?.node?.basicMedia?.toDomain(), it?.relationType?.toDomain()) }
    }

    fun ApolloMediaTag.toDomain(): DomainMediaTag {
        return DomainMediaTag(id, name, description, category, rank, isGeneralSpoiler, isMediaSpoiler, isAdult)
    }

    fun ApolloMediaTrailer.toDomain(): DomainMediaTrailer {
        return DomainMediaTrailer(id, site, thumbnail)
    }

    fun ApolloBasicStats.toDomain(): DomainBasicStats {
        return DomainBasicStats(
            scoreDistribution?.mapNotNull {
                if (it?.score != null && it.amount != null) Pair(it.score, it.amount) else null
            },
            statusDistribution?.mapNotNull {
                if (it?.status != null && it.amount != null) Pair(it.status.toDomain(), it.amount) else null
            }
        )
    }

    fun ApolloMediaStreamingEpisode.toDomain(): DomainMediaStreamingEpisode {
        return DomainMediaStreamingEpisode(title, thumbnail, url, site)
    }
}