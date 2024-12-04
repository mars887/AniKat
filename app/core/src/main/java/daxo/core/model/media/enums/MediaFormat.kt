package daxo.core.model.media.enums

enum class MediaFormat(
    val rawValue: String,
    val value: String,
) {
    TV("TV", "TV"),
    TV_SHORT("TV_SHORT", "TV short"),
    MOVIE("MOVIE", "Movie"),
    SPECIAL("SPECIAL", "Special"),
    OVA("OVA", "OVA"),
    ONA("ONA", "ONA"),
    MUSIC("MUSIC", "Music"),
    MANGA("MANGA", "Manga"),
    NOVEL("NOVEL", "Novel"),
    ONE_SHOT("ONE_SHOT", "One shot"),
    UNKNOWN__("UNKNOWN__", "Unknown");

    fun safeValueOf(value: String): MediaFormat =
        entries.find { it.rawValue == value } ?: entries.find { it.value == value } ?: UNKNOWN__
}
