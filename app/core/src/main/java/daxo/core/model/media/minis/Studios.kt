package daxo.core.model.media.minis

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Studios(
    val edges: List<Node>?,
): Parcelable {
    @Parcelize
    data class Node(
        val isMain: Boolean?,
        val id: Int?,
        val name: String?,
    ): Parcelable
}