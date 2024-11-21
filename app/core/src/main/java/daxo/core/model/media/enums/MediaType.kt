package daxo.core.model.media.enums

enum class MediaType(
    val rawValue: String,
) {
    ANIME("ANIME"),
    MANGA("MANGA"),
    UNKNOWN__("UNKNOWN__");

    fun safeValueOf(rawValue: String): MediaType = entries.find { it.rawValue == rawValue } ?: UNKNOWN__

}
