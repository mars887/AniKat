package daxo.core.model.media.enums

enum class MediaRelationType(
    val rawValue: String,
) {
    /**
     * An adaption of this media into a different format
     */
    ADAPTATION("ADAPTATION"),

    /**
     * Released before the relation
     */
    PREQUEL("PREQUEL"),

    /**
     * Released after the relation
     */
    SEQUEL("SEQUEL"),

    /**
     * The media a side story is from
     */
    PARENT("PARENT"),

    /**
     * A side story of the parent media
     */
    SIDE_STORY("SIDE_STORY"),

    /**
     * Shares at least 1 character
     */
    CHARACTER("CHARACTER"),

    /**
     * A shortened and summarized version
     */
    SUMMARY("SUMMARY"),

    /**
     * An alternative version of the same media
     */
    ALTERNATIVE("ALTERNATIVE"),

    /**
     * An alternative version of the media with a different primary focus
     */
    SPIN_OFF("SPIN_OFF"),

    /**
     * Other
     */
    OTHER("OTHER"),

    /**
     * Version 2 only. The source material the media was adapted from
     */
    SOURCE("SOURCE"),

    /**
     * Version 2 only.
     */
    COMPILATION("COMPILATION"),

    /**
     * Version 2 only.
     */
    CONTAINS("CONTAINS"),

    /**
     * Auto generated constant for unknown enum values
     */
    UNKNOWN__("UNKNOWN__");

    companion object {
        fun safeValueOf(rawValue: String): MediaRelationType = entries.find { it.rawValue == rawValue } ?: UNKNOWN__
    }
}
