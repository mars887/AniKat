package daxo.the.navigation.simpletest

import android.os.Bundle
import androidx.annotation.IdRes

class NavigationHelper(
    private val controller: NavController2
) {

    fun navigate(@IdRes id: Int, bundle: Bundle? = null) {
        controller.navigateTo(id,bundle)
    }

    fun switchBackStack(backstack: String) {
        controller.switchBackStack(backstack)
    }

    fun popBackStack(): Boolean = controller.popBackStack()
}

fun NavController2.toHelper(): NavigationHelper = NavigationHelper(this)