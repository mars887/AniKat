package daxo.core.model.media.enums

enum class MediaRankType(
    val rawValue: String,
) {
    /**
     * Ranking is based on the media's ratings/score
     */
    RATED("RATED"),

    /**
     * Ranking is based on the media's popularity
     */
    POPULAR("POPULAR"),

    /**
     * Auto generated constant for unknown enum values
     */
    UNKNOWN__("UNKNOWN__");


    companion object {
        fun safeValueOf(rawValue: String): MediaRankType = entries.find { it.rawValue == rawValue } ?: UNKNOWN__
    }

}
