package daxo.the.navigation.simpletest

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigator
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import daxo.the.navigation.BackStackInfo
import daxo.the.navigation.BackstackList

class NavController2 @AssistedInject constructor(
    @Assisted @IdRes private val host: Int,
    @Assisted defaultBackstackKey: String,
    @Assisted _backstacksList: Map<String, Int>,
    @Assisted private val fragmentManager: FragmentManager,
    @Assisted private val navController: NavController,
) {

    private val backstacksList: BackstackList =
        BackstackList(_backstacksList.map { it.key to BackStackInfo(it.key, it.value) }.toMap())

    private var currentBackstack = backstacksList.stacks[defaultBackstackKey] ?: error("initial backstack key not found")
    private val initializedBackstacks = mutableSetOf<String>()

    init {
        fragmentManager.popBackStackImmediate()
        initBackStack(currentBackstack)
    }

    private fun initBackStack(backstack: BackStackInfo, andSaveState: Boolean = false) {
        fragmentManager.commit {
            replace(host, backstack.rootId.toClass(), null, null)
            setReorderingAllowed(true)
            addToBackStack(backstack.key)
        }
        initializedBackstacks += backstack.key
        if (andSaveState) fragmentManager.saveBackStack(backstack.key)
    }

    fun navigateTo(@IdRes id: Int, bundle: Bundle? = null) {
        if (currentBackstack.key !in initializedBackstacks) initBackStack(currentBackstack)
        fragmentManager.commit {
            replace(host, id.toClass(), bundle, null)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

    fun switchBackStack(_backstack: String) {
        val backstack = backstacksList.stacks[_backstack] ?: error("backstack key $_backstack not found")
        fragmentManager.saveBackStack(currentBackstack.key)
        if (backstack.key !in initializedBackstacks) {
            initBackStack(backstack)
        } else {
            fragmentManager.restoreBackStack(backstack.key)
        }
        currentBackstack = backstack
    }

    private fun Int.toClass(): Class<out Fragment> {
        val className = (navController.graph.findNode(this) as FragmentNavigator.Destination).className
        return Class.forName(className).asSubclass(Fragment::class.java)
    }

    fun popBackStack(): Boolean {
        return fragmentManager.popBackStackImmediate()
    }

    companion object {
        private const val TAG = "NavController2"
    }
}