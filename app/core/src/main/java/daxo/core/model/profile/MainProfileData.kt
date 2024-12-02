package daxo.core.model.profile

import daxo.core.model.media.enums.UserStaffNameLanguage
import daxo.core.model.media.enums.UserTitleLanguage

data class MainProfileData(
    /**
     * The id of the user
     */
    val id: Int,
    /**
     * The name of the user
     */
    val name: String,
    /**
     * The bio written by user (Markdown)
     */
    val about: String?,
    /**
     * The user's banner images
     */
    val bannerImage: String?,
    /**
     * The number of unread notifications the user has
     */
    val unreadNotificationCount: Int?,
    /**
     * The url for the user page on the AniList website
     */
    val siteUrl: String?,
    /**
     * The donation tier of the user
     */
    val donatorTier: Int?,
    /**
     * Custom donation badge text
     */
    val donatorBadge: String?,
    /**
     * The user's general options
     */
    val userOptions: UserOptions?,
    /**
     * The user's avatar images
     */
    val avatar: Avatar?,
) {

    data class UserOptions(
        /**
         * The language the user wants to see media titles in
         */
        val titleLanguage: UserTitleLanguage?,
        /**
         * Whether the user has enabled viewing of 18+ content
         */
        val displayAdultContent: Boolean?,
        /**
         * Profile highlight color (blue, purple, pink, orange, red, green, gray)
         */
        val profileColor: String?,
        /**
         * The language the user wants to see staff and character names in
         */
        val staffNameLanguage: UserStaffNameLanguage?,
    )

    data class Avatar(
        /**
         * The avatar of user at its largest size
         */
        val large: String?,
        /**
         * The avatar of user at medium size
         */
        val medium: String?,
    )
}