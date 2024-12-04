package daxo.core.model.media.enums

enum class UserTitleLanguage(
    val rawValue: String,
) {
    /**
     * The romanization of the native language title
     */
    ROMAJI("ROMAJI"),
    /**
     * The official english title
     */
    ENGLISH("ENGLISH"),
    /**
     * Official title in it's native language
     */
    NATIVE("NATIVE"),
    /**
     * The romanization of the native language title, stylised by media creator
     */
    ROMAJI_STYLISED("ROMAJI_STYLISED"),
    /**
     * The official english title, stylised by media creator
     */
    ENGLISH_STYLISED("ENGLISH_STYLISED"),
    /**
     * Official title in it's native language, stylised by media creator
     */
    NATIVE_STYLISED("NATIVE_STYLISED"),
    /**
     * Auto generated constant for unknown enum values
     */
    UNKNOWN__("UNKNOWN__"),
    ;

    companion object {
        /**
         * Returns the [UserTitleLanguage] that represents the specified [rawValue].
         * Note: unknown values of [rawValue] will return [UNKNOWN__]. You may want to update your schema instead of calling this function directly.
         */
        fun safeValueOf(rawValue: String): UserTitleLanguage = entries.find { it.rawValue == rawValue } ?: UNKNOWN__
    }
}
