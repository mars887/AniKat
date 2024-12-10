package daxo.core.model.media.enums

enum class MediaSeason(
    val rawValue: String,
) {
    WINTER("WINTER"),
    SPRING("SPRING"),
    SUMMER("SUMMER"),
    FALL("FALL"),
    UNKNOWN__("UNKNOWN__");

    companion object {
        fun safeValueOf(rawValue: String): MediaSeason = entries.find { it.rawValue == rawValue } ?: UNKNOWN__
    }
}