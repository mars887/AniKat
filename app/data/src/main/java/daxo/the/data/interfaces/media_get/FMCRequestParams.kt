package daxo.the.data.interfaces.media_get

import daxo.core.model.media.enums.StaffLanguage

data class FMCRequestParams(
    val innerListsPage: Int = 1,
    val innerListsPerPage: Int = 20,
    val voiceActorsLanguage: StaffLanguage = StaffLanguage.JAPANESE
)