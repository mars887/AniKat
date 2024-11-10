package daxo.the.navigation

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
//
//internal class FragmentWID(
//    val fragment: Fragment,
//    @IdRes val id: Int,
//) {
//
//    /**
//    equals by id
//     */
//    override fun equals(other: Any?): Boolean {
//        return if(other is FragmentWID) id == other.id && fragment == other.fragment else false
//    }
//
//    fun equalsId(other: FragmentWID): Boolean = other.id == id
//
//    /**
//    hash by fragment and id
//     */
//    override fun hashCode(): Int {
//        var result = fragment.hashCode()
//        result = 31 * result + id
//        return result
//    }
//}