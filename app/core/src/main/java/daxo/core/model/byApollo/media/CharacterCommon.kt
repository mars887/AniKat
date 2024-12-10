package daxo.core.model.byApollo.media

import android.os.Parcelable
import daxo.core.model.media.enums.CharacterRole
import kotlinx.parcelize.Parcelize

@Parcelize
data class BasicCharacterQuery(
    val role: CharacterRole?,
    val character: BasicCharacter?,
    val voiceActor: List<BasicStaff>?,
) : Parcelable

@Parcelize
data class BasicCharacter(
    val id: Int?,
    val name: BasicCharacterName?,
    val image: CharacterImage?,
    val age: String?
) : Parcelable

@Parcelize
data class CharacterImage(
    val large: String?,
    val medium: String?,
) : Parcelable

@Parcelize
data class BasicCharacterName(
    val first: String?,
    val last: String?,
    val full: String?
) : Parcelable