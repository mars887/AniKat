package daxo.the.data.api.explore

import android.util.Log
import daxo.the.domain.model.media.enums.MediaFormat
import daxo.the.domain.model.media.enums.MediaSeason
import daxo.the.domain.model.media.enums.MediaSort
import daxo.the.domain.model.media.enums.MediaType

data class LoadMediaPagesParams(
    var page: Int = -1,
    val perPage: Int = 10,
    val type: MediaType = MediaType.ANIME,
    val sort: List<MediaSort> = listOf(MediaSort.POPULARITY_DESC),
    val season: MediaSeason? = null,
    val seasonYear: Int? = null,
    val genre: String? = null,
    val format: MediaFormat? = null,
    val search: String? = null,
    val isAdult: Boolean? = null,
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LoadMediaPagesParams

        if (type != other.type) return false
        if (sort != other.sort) return false
        if (season != other.season) return false
        if (seasonYear != other.seasonYear) return false
        if (genre != other.genre) return false
        if (format != other.format) return false
        if (search != other.search) return false
        if (isAdult != other.isAdult) return false

        return true
    }

    override fun hashCode(): Int {
        var result = type.hashCode()
        result = 31 * result + sort.hashCode()
        result = 31 * result + (season?.hashCode() ?: 0)
        result = 31 * result + (seasonYear ?: 0)
        result = 31 * result + (genre?.hashCode() ?: 0)
        result = 31 * result + (format?.hashCode() ?: 0)
        result = 31 * result + (search?.hashCode() ?: 0)
        result = 31 * result + (isAdult?.hashCode() ?: 0)
        return result
    }

    fun toRequestData(): String {
        val sort = this.sort.joinToString(",") { it.rawValue }
        val type = this.type.rawValue
        val season = this.season?.rawValue ?: ""
        val format = this.format?.rawValue ?: ""
        return "$page:$perPage:$type:$sort:$season:$seasonYear:${genre ?: ""}:$format:${search ?: ""}:$isAdult"
    }

    companion object {
        fun toParams(text: String): LoadMediaPagesParams {
            val data = text.split(":")
            val type = MediaType.valueOf(data[2])
            val sort = data[3].split(",").map { MediaSort.valueOf(it) }
            val season = if(data[4].isBlank()) null else MediaSeason.valueOf(data[4])
            val format = if(data[7].isBlank()) null else MediaFormat.valueOf(data[7])

            return LoadMediaPagesParams(
                page = data[0].toInt(),
                perPage = data[1].toInt(),
                type = type, // [2]
                sort = sort, // [3]
                season = season,//[4]
                seasonYear = data[5].toIntOrNull(),
                genre = data[6].takeIf { it.isNotBlank() },
                format = format,//[7]
                search = data[8].takeIf { it.isNotBlank() },
                isAdult = data[9].toBooleanStrictOrNull()
            )
        }

        fun nextPageRequest(text: String): LoadMediaPagesParams {
            val request = toParams(text)
            Log.i(TAG, "nextPageRequest: switch page to ${request.page + 1}")
            request.page++
            return request
        }

        private const val TAG = "LoadMediaPagesParams"
    }
}