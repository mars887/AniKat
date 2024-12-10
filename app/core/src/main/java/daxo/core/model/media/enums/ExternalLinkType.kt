package daxo.core.model.media.enums

enum class ExternalLinkType(
    val rawValue: String,
) {
    INFO("INFO"),
    STREAMING("STREAMING"),
    SOCIAL("SOCIAL"),

    /**
     * Auto generated constant for unknown enum values
     */
    UNKNOWN__("UNKNOWN__");

    companion object {
        fun safeValueOf(rawValue: String): ExternalLinkType = entries.find { it.rawValue == rawValue } ?: UNKNOWN__
    }
}
