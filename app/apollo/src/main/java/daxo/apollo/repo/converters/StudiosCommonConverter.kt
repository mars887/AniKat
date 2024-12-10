package daxo.apollo.repo.converters

import daxo.core.model.byApollo.media.BasicStudioConnection
import daxo.core.model.byApollo.media.BasicStudio as DomainBasicStudio
import daxo.core.model.byApollo.media.BasicStudioQuery as DomainBasicStudioQuery
import daxo.the.apollo.fragment.BasicStudio as ApolloBasicStudio
import daxo.the.apollo.fragment.BasicStudioQuery as ApolloBasicStudioQuery

object StudiosCommonConverter {

    fun ApolloBasicStudio.toDomain(): DomainBasicStudio {
        return DomainBasicStudio(id, name, isFavourite)
    }

    fun ApolloBasicStudioQuery.toDomain(): DomainBasicStudioQuery? {
        if (edges == null) return null
        return DomainBasicStudioQuery(
            edges.map {
                BasicStudioConnection(it?.id, it?.isMain, it?.node?.basicStudio?.toDomain())
            }
        )
    }
}