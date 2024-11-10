package daxo.the.navigation

import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import java.util.ArrayDeque
import java.util.EnumMap

//internal class BackstackController(
//    private val fragmentManager: FragmentManager,
//    private val currentBackstackKey: MutableWrapper<BackstackList>,
//    private val backstacksConfigs: EnumMap<BackstackList, NavigationBackstackConfig>,
//    private val fragmentsConfigs: HashMap<Int, FragmentConfig>,
//    private val fragmentsProvider: FragmentsProvider,
//) {

//    private val backstacks: EnumMap<BackstackList, ArrayDeque<FragmentWID>> = EnumMap(
//        BackstackList.entries.associateWith { ArrayDeque<FragmentWID>() }
//    )

//    init {
//
//        backstacks.forEach { (key, backstack) ->
//            val config = backstacksConfigs[key]!!
//            val fragmentId = config.rootFragmentId
//
//            if (fragmentId != null) {
//                val fragment = fragmentsProvider.createById(fragmentId)
//                backstack.push(fragment)
//                fragmentManager.commit {
//                    add(fragment.fragment, "root")
//                    disable(fragment)
//                }
//            }
//        }
//    }
//
//    /**
//     * returns true if need exit from app
//     */
//    fun popBackStack(): Boolean {
//        val backstack = backstacks[currentBackstackKey.value]!!
//        if (backstack.size == 1) {
//
//            val fragment = backstack.peek()!!
//            val fConfigs = fragmentsConfigs[fragment.id]!!
//            val backstackBackPolicy =
//                backstacksConfigs[currentBackstackKey.value]!!.backPressedPolicy
//
//            if (fConfigs.backstackPolicy == BackstackPolicy.ONLY_ROOT) {
//
//                return if (backstackBackPolicy == BackPressedPolicy.ExitApp) {
//                    true
//                } else {
//                    val backstackToRoot = backstackBackPolicy as BackPressedPolicy.BackstackToRoot
//                    when (backstackToRoot.endPolicy) {
//
//                        BackstackEndPolicy.EXIT_APP -> return true
//
//                        BackstackEndPolicy.CHANGE_BACKSTACK_TO -> {
//                            if (backstackToRoot.changeBackstackTo != null) {
//                                switchBackStack(backstackToRoot.changeBackstackTo)
//                                false
//                            } else {
//                                Log.w(
//                                    TAG,
//                                    "popBackStack: on backstack ${currentBackstackKey.value} set " +
//                                            "BackstackEndPolicy.CHANGE_BACKSTACK_TO but changeBackstackTo not configured"
//                                )
//                                false
//                            }
//                        }
//
//                        BackstackEndPolicy.OPEN_ROOT_FRAGMENT -> false
//
//                        BackstackEndPolicy.NOTHING -> false
//
//                    }
//                }
//            } else {
//                forcePopBackStack(backstack)
//                if (backstackBackPolicy == BackPressedPolicy.ExitApp) return true
//                else {
//                    val backstackToRoot = backstackBackPolicy as BackPressedPolicy.BackstackToRoot
//                    if (backstackToRoot.endPolicy == BackstackEndPolicy.CHANGE_BACKSTACK_TO && backstackToRoot.changeBackstackTo != null) {
//                        switchBackStack(backstackToRoot.changeBackstackTo)
//                    } else {
//                        return true
//                    }
//                }
//            }
//        }
//        TODO()
//    }
//
//    private fun forcePopBackStack(backstack: ArrayDeque<FragmentWID>) {
//        val fragment = backstack.pop()
//        fragmentManager.commit {
//            remove(fragment.fragment)
//        }
//    }
//
//    fun switchBackStack(backstackKey: BackstackList) {
//
//    }
//
//    fun navigate(newFragmentId: Int): FragmentTransactionResult {
//        val backstack = backstacks[currentBackstackKey.value]!!
//        val fragmentConfig = fragmentsConfigs[newFragmentId]!!
//
//        if (fragmentConfig.allowedBackstackKeys != null && !fragmentConfig.allowedBackstackKeys.contains(
//                currentBackstackKey.value
//            )
//        )
//            return FragmentTransactionResult.IncompatibleBackstack("fragmentId $newFragmentId not allowed in backstack ${currentBackstackKey.value}")
//
//
//        return when (fragmentConfig.backstackPolicy) {
//
//            BackstackPolicy.SINGLE_INSTANCE_CLEAR_UPPER -> {
//                val found = backstack.findLast { it.id == newFragmentId }
//
//                if (found != null) {
//                    val oldFragment = backstack.peek()!!
//                    while (backstack.peek()!!.id != newFragmentId) {
//                        backstack.destroyLast()
//                    }
//                    forcePutFragment(backstack, backstack.peek()!!, oldFragment)
//                    FragmentTransactionResult.Success()
//                } else {
//                    forcePutFragment(backstack, fragmentsProvider.createById(newFragmentId))
//                    FragmentTransactionResult.Success()
//                }
//            }
//
//            BackstackPolicy.SINGLE_INSTANCE_MOVE_UP -> {
//                val found = backstack.findLast { it.id == newFragmentId }
//
//                if (found != null) {
//                    backstack.remove(found)
//                    forcePutFragment(backstack, found)
//                    FragmentTransactionResult.Success()
//                } else {
//                    forcePutFragment(backstack, fragmentsProvider.createById(newFragmentId))
//                    FragmentTransactionResult.Success()
//                }
//            }
//
//            BackstackPolicy.ONLY_ROOT -> {
//                if (backstack.isEmpty()) {
//                    forcePutFragment(backstack, fragmentsProvider.createById(newFragmentId))
//                    FragmentTransactionResult.Success()
//                } else FragmentTransactionResult.OnlyRootFragment()
//            }
//
//            BackstackPolicy.SINGLE_TOP -> {
//                if (backstack.peek()!!.id == fragmentConfig.fragmentId) {
//                    FragmentTransactionResult.ErrorSingleTopPolicy()
//                } else {
//                    forcePutFragment(backstack, fragmentsProvider.createById(newFragmentId))
//                    FragmentTransactionResult.Success()
//                }
//            }
//
//            BackstackPolicy.ANY -> {
//                forcePutFragment(backstack, fragmentsProvider.createById(newFragmentId))
//                FragmentTransactionResult.Success()
//            }
//        }
//    }
//
//
//    private fun forcePutFragment(
//        backstack: ArrayDeque<FragmentWID>,
//        newFragment: FragmentWID,
//        _oldFragment: FragmentWID? = null
//    ) {
//        val oldFragment = _oldFragment ?: backstack.peek()
//        if (oldFragment != null) {
//            fragmentManager.commit {
//                disable(newFragment)
//            }
//        }
//        backstack.push(newFragment)
//        fragmentManager.commit {
//            enable(newFragment)
//        }
//    }
//
//    private fun FragmentTransaction.disable(fragment: FragmentWID) {
//        val controlFunction = fragmentsConfigs[fragment.id]!!.controlFunctions
//        when (controlFunction) {
//            ControlFunctions.SHOW_HIDE -> hide(fragment.fragment)
//            ControlFunctions.ATTACH_DETACH -> detach(fragment.fragment)
//        }
//    }
//
//    private fun FragmentTransaction.enable(fragment: FragmentWID) {
//        val controlFunction = fragmentsConfigs[fragment.id]!!.controlFunctions
//        when (controlFunction) {
//            ControlFunctions.SHOW_HIDE -> show(fragment.fragment)
//            ControlFunctions.ATTACH_DETACH -> attach(fragment.fragment)
//        }
//    }
//
//    private fun ArrayDeque<out FragmentWID>.destroyLast() {
//        fragmentManager.commit {
//            this.remove(pop().fragment)
//        }
//    }
//
//    companion object {
//        private const val TAG = "BackstackController"
//    }
//}

