package daxo.the.domain.model.media

import java.util.Date

data class BasicMediaCard(
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
) {
    fun toBundleString(): String {
        val genres = genres.joinToString(",")
        return "$mediaId~:~$title~:~$description~:~$episodes~:~$genres~:~$averageScope~:~$favourites~:~$coverImageEL~:~$bannerImage~:~${lastUpdate.time}"
    }

    companion object {
        fun parseFromBundleString(data: String): BasicMediaCard {
            val tokens = data.split("~:~")
            val genres = tokens[4].split(",")
            return BasicMediaCard(
                mediaId = tokens[0].toInt(),
                title = tokens[1],
                description = tokens[2],
                episodes = tokens[3].toInt(),
                genres = genres,
                averageScope = tokens[5].toInt(),
                favourites = tokens[6].toInt(),
                coverImageEL = tokens[7],
                bannerImage = tokens[8],
                lastUpdate = Date(tokens[9].toLong())
            )
        }
    }
}