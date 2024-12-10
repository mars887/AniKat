package daxo.core.model.media.enums

enum class CharacterRole(
    val rawValue: String,
) {
    /**
     * A primary character role in the media
     */
    MAIN("MAIN"),

    /**
     * A supporting character role in the media
     */
    SUPPORTING("SUPPORTING"),

    /**
     * A background character in the media
     */
    BACKGROUND("BACKGROUND"),

    /**
     * Auto generated constant for unknown enum values
     */
    UNKNOWN__("UNKNOWN__");

    companion object {
        fun safeValueOf(rawValue: String): CharacterRole = entries.find { it.rawValue == rawValue } ?: UNKNOWN__
    }
}