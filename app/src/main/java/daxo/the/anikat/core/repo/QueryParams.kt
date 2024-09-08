package daxo.the.anikat.core.repo

import daxo.the.anikat.type.MediaFormat
import daxo.the.anikat.type.MediaSeason
import daxo.the.anikat.type.MediaSort
import daxo.the.anikat.type.MediaType

data class QueryParams(
    val type: MediaType?,
    val sort: List<MediaSort>?,
    val season: MediaSeason? = null,
    val seasonYear: Int? = null,
    val genre: String? = null,
    val format: MediaFormat? = null,
    val search: String? = null,
    val isAdult: Boolean? = null
)