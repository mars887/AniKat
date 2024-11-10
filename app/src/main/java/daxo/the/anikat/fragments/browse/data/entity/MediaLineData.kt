package daxo.the.anikat.fragments.browse.data.entity

import java.util.concurrent.atomic.AtomicInteger
import kotlin.random.Random

data class MediaLineData(
    val lineName: String,
    var data: MutableList<MediaCardData>,
    val tag: ExploreMediaPagesInfo.MediaTypes,
    val scrollPosition: AtomicInteger = AtomicInteger(0),
) {

    val unic = Random.nextInt()

    init {
        //println("MediaLineData created $lineName $tag $scrollPosition")
    }
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