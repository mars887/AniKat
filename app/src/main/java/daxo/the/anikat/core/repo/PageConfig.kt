package daxo.the.anikat.core.repo

import daxo.the.anikat.fragments.browse.data.entity.ExploreMediaPagesInfo

sealed class PageConfig(val perPage: Int = PER_PAGE_DEFAULT) {
    class NextPage(
        val pageKey: ExploreMediaPagesInfo.MediaTypes,
        val fromStart: Boolean = false,
        perPage: Int = PER_PAGE_DEFAULT
    ) : PageConfig(perPage)

    class NumberedPage(
        val pageNumber: Int,
        perPage: Int = PER_PAGE_DEFAULT
    ) : PageConfig(perPage)

    private companion object {
        const val PER_PAGE_DEFAULT = 10
    }
}
