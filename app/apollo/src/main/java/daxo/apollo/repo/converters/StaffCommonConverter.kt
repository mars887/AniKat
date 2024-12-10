package daxo.apollo.repo.converters

import daxo.the.apollo.fragment.BasicStaffName as ApolloBasicStaffName
import daxo.core.model.byApollo.media.BasicStaffName as DomainBasicStaffName
import daxo.core.model.byApollo.media.StaffImage as DomainStaffImage
import daxo.the.apollo.fragment.StaffImage as ApolloStaffImage
import daxo.core.model.byApollo.media.BasicStaff as DomainBasicStaff
import daxo.the.apollo.fragment.BasicStaff as ApolloBasicStaff
import daxo.the.apollo.fragment.BasicStaffQuery as ApolloBasicStaffQuery
import daxo.core.model.byApollo.media.BasicStaffQuery as DomainBasicStaffQuery

object StaffCommonConverter {
    fun ApolloBasicStaffQuery.toDomain(): DomainBasicStaffQuery? {
        if(edges == null) return null
        return DomainBasicStaffQuery(edges.mapNotNull {
            it?.node?.basicStaff?.toDomain()
        })
    }

    fun ApolloBasicStaff.toDomain(): DomainBasicStaff {
        return DomainBasicStaff(id, name?.basicStaffName?.toDomain(),languageV2, image?.staffImage?.toDomain())
    }

    fun ApolloStaffImage.toDomain(): DomainStaffImage {
        return DomainStaffImage(large, medium)
    }

    fun ApolloBasicStaffName.toDomain(): DomainBasicStaffName {
        return DomainBasicStaffName(first,last,full)
    }
}