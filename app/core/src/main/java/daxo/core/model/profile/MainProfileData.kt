package daxo.core.model.profile

import daxo.core.model.byApollo.media.UserAvatar

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
     * The user's avatar images
     */
    val avatar: UserAvatar?,
) {
}