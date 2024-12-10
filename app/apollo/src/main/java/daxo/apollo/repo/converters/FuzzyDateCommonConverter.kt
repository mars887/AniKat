package daxo.apollo.repo.converters

import daxo.the.apollo.fragment.FuzzyDate as ApolloFuzzyDate
import daxo.core.model.byApollo.media.FuzzyDate as DomainFuzzyDate

object FuzzyDateCommonConverter {
    fun ApolloFuzzyDate.toDomain(): DomainFuzzyDate {
        return DomainFuzzyDate(day,month,year)
    }
}