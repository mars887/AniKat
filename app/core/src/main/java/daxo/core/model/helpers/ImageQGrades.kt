package daxo.core.model.helpers

import android.os.Parcelable
import daxo.core.model.byApollo.media.CharacterImage
import daxo.core.model.byApollo.media.MediaCoverImage
import daxo.core.model.byApollo.media.StaffImage
import daxo.core.model.byApollo.media.UserAvatar
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageQGrades(
    val max: String?,
    val mid: String?
) : Parcelable {

    constructor(userAvatar: UserAvatar) : this(userAvatar.large ?: userAvatar.medium, userAvatar.medium)
    constructor(characterImage: CharacterImage) : this(characterImage.large ?: characterImage.medium, characterImage.medium)
    constructor(staffImage: StaffImage) : this(staffImage.large ?: staffImage.medium, staffImage.medium)
    constructor(mediaCoverImage: MediaCoverImage) : this(
        mediaCoverImage.extraLarge ?: mediaCoverImage.large ?: mediaCoverImage.medium,
        mediaCoverImage.large ?: mediaCoverImage.medium,
    )

    constructor(imageUrl: String) : this(imageUrl, imageUrl)
}