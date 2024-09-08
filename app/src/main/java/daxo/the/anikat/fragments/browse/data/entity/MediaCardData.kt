package daxo.the.anikat.fragments.browse.data.entity

import daxo.the.anikat.type.MediaType

data class MediaCardData(
    val mediaType: MediaType,
    val mediaId: Int,
    val title: String,
    val episodes: String,
    val genres: List<String>,
    val averageScore: String,
    val favorites: Int,
    val seasonYear: Int,
    val coverImageLink: String
) {
    override fun equals(other: Any?): Boolean {
        return other is MediaCardData && other.mediaId == mediaId
    }

    override fun hashCode(): Int {
        var result = mediaType.hashCode()
        result = 31 * result + mediaId
        result = 31 * result + title.hashCode()
        return result
    }
}