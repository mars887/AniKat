package daxo.the.navigation

import androidx.annotation.IdRes
//
//data class NavigationBackstackConfig(
//    val backstackKey: BackstackList,
//    @IdRes val rootFragmentId: Int? = null,
//    val backPressedPolicy: BackPressedPolicy,
//) {
//}
//
//sealed class BackPressedPolicy {
//    data object ExitApp : BackPressedPolicy()
//    data class BackstackToRoot(
//        val endPolicy: BackstackEndPolicy,
//        val changeBackstackTo: BackstackList? = null
//    ) : BackPressedPolicy()
//}
//
//enum class BackstackEndPolicy {
//    EXIT_APP, CHANGE_BACKSTACK_TO, OPEN_ROOT_FRAGMENT, NOTHING
//}