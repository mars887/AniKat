package daxo.the.domain.model

import java.util.Date

data class BasicMediaPageViewed(
    val mediaId: Int,
    val title: String? = null,
    val description: String? = null,
    val episodes: Int? = null,
    val genres: List<String>? = null,
    val averageScope: Int? = null,
    val favourites: Int? = null,
    val coverImageEL: String? = null,
    val bannerImage: String? = null,
    val lastUpdate: Date? = null,
    val lastViewed: Date
)