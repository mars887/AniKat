package daxo.the.anikat.core.repo

import daxo.the.anikat.fragments.browse.data.entity.ExploreMediaPagesInfo
import javax.inject.Inject

class PagesController @Inject constructor() {

    private val map = HashMap<ExploreMediaPagesInfo.MediaTypes, Int>()

    fun getPageIdFor(pageConfig: PageConfig): Int {
        return when (pageConfig) {
            is PageConfig.NextPage -> {
                if (pageConfig.fromStart) map.remove(pageConfig.pageKey)
                (map[pageConfig.pageKey] ?: 1).also { map[pageConfig.pageKey] = it + 1 }
            }

            is PageConfig.NumberedPage -> pageConfig.pageNumber
        }
    }
}