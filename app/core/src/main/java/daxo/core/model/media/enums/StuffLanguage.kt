package daxo.core.model.media.enums

enum class StaffLanguage(
    val rawValue: String,
) {
    /**
     * Japanese
     */
    JAPANESE("JAPANESE"),

    /**
     * English
     */
    ENGLISH("ENGLISH"),

    /**
     * Korean
     */
    KOREAN("KOREAN"),

    /**
     * Italian
     */
    ITALIAN("ITALIAN"),

    /**
     * Spanish
     */
    SPANISH("SPANISH"),

    /**
     * Portuguese
     */
    PORTUGUESE("PORTUGUESE"),

    /**
     * French
     */
    FRENCH("FRENCH"),

    /**
     * German
     */
    GERMAN("GERMAN"),

    /**
     * Hebrew
     */
    HEBREW("HEBREW"),

    /**
     * Hungarian
     */
    HUNGARIAN("HUNGARIAN"),

    /**
     * Auto generated constant for unknown enum values
     */
    UNKNOWN__("UNKNOWN__");


    companion object {
        fun safeValueOf(rawValue: String): StaffLanguage = entries.find { it.rawValue == rawValue } ?: UNKNOWN__
    }
}
