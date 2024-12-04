package daxo.core.model.media.enums

enum class UserStaffNameLanguage(
    val rawValue: String,
) {
    /**
     * The romanization of the staff or character's native name, with western name ordering
     */
    ROMAJI_WESTERN("ROMAJI_WESTERN"),

    /**
     * The romanization of the staff or character's native name
     */
    ROMAJI("ROMAJI"),

    /**
     * The staff or character's name in their native language
     */
    NATIVE("NATIVE"),

    /**
     * Auto generated constant for unknown enum values
     */
    UNKNOWN__("UNKNOWN__"),
    ;

    companion object {
        /**
         * Returns the [UserStaffNameLanguage] that represents the specified [rawValue].
         * Note: unknown values of [rawValue] will return [UNKNOWN__]. You may want to update your schema instead of calling this function directly.
         */
        fun safeValueOf(rawValue: String): UserStaffNameLanguage = entries.find { it.rawValue == rawValue } ?: UNKNOWN__
    }
}