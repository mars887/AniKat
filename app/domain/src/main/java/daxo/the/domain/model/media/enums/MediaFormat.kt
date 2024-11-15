package daxo.the.domain.model.media.enums

enum class MediaFormat(
    val rawValue: String,
) {
    TV("TV"),
    TV_SHORT("TV_SHORT"),
    MOVIE("MOVIE"),
    SPECIAL("SPECIAL"),
    OVA("OVA"),
    ONA("ONA"),
    MUSIC("MUSIC"),
    MANGA("MANGA"),
    NOVEL("NOVEL"),
    ONE_SHOT("ONE_SHOT"),
    UNKNOWN__("UNKNOWN__");

    fun safeValueOf(rawValue: String): MediaFormat =
        entries.find { it.rawValue == rawValue } ?: UNKNOWN__
}
