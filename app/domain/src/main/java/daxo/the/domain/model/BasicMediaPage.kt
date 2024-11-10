package daxo.the.domain.model

import java.util.Date

data class BasicMediaPage(
    val mediaId: Int,
    val title: String,
    val description: String,
    val episodes: Int,
    val genres: List<String>,
    val averageScope: Int,
    val favourites: Int,
    val coverImageEL: String,
    val bannerImage: String,
    val lastUpdate: Date,
)