package daxo.the.anikat.fragments.browse.data.entity

data class MediaLineData(
    val lineName: String,
    var data: MutableList<MediaCardData>,
    val tag: ExploreMediaPagesInfo.MediaTypes,
) {
    override fun equals(other: Any?): Boolean {
        return other is MediaLineData &&
                other.lineName == lineName &&
                data == other.data &&
                tag == other.tag
    }

    override fun hashCode(): Int {
        var result = lineName.hashCode()
        result = 31 * result + tag.hashCode()
        result = 31 * result + data.hashCode()
        return result
    }
}