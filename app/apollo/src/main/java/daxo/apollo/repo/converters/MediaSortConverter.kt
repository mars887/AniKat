package daxo.apollo.repo.converters

import daxo.the.apollo.type.MediaSort as ApolloMediaSort
import daxo.core.model.media.enums.MediaSort as DomainMediaSort

object MediaSortConverter {
    fun toDomain(mediaType: ApolloMediaSort): DomainMediaSort {
        return DomainMediaSort.valueOf(mediaType.rawValue)
    }

    fun convertToApollo(mediaType: DomainMediaSort): ApolloMediaSort {
        return ApolloMediaSort.valueOf(mediaType.rawValue)
    }

    fun DomainMediaSort.toApollo(): ApolloMediaSort {
        return ApolloMediaSort.valueOf(this.rawValue)
    }
}