package daxo.core.model.media.enums

enum class MediaListStatus(
    val rawValue: String,
) {
    /**
     * Currently watching/reading
     */
    CURRENT("CURRENT"),

    /**
     * Planning to watch/read
     */
    PLANNING("PLANNING"),

    /**
     * Finished watching/reading
     */
    COMPLETED("COMPLETED"),

    /**
     * Stopped watching/reading before completing
     */
    DROPPED("DROPPED"),

    /**
     * Paused watching/reading
     */
    PAUSED("PAUSED"),

    /**
     * Re-watching/reading
     */
    REPEATING("REPEATING"),

    /**
     * Auto generated constant for unknown enum values
     */
    UNKNOWN__("UNKNOWN__");

    companion object {
        fun safeValueOf(rawValue: String): MediaListStatus = entries.find { it.rawValue == rawValue } ?: UNKNOWN__
    }

}
