package daxo.the.navigation

import androidx.annotation.IdRes
import androidx.lifecycle.ViewModel
import kotlin.reflect.KClass


//data class FragmentConfig(
//    val fragmentClass: KClass<out androidx.fragment.app.Fragment>,
//    @IdRes val fragmentId: Int,
//    val backstackPolicy: BackstackPolicy = BackstackPolicy.ANY,
//    val controlFunctions: ControlFunctions = ControlFunctions.ATTACH_DETACH,
//    val allowBackstackTo: Set<Class<out androidx.fragment.app.Fragment>>? = null,
//    val allowedBackstackKeys: Set<BackstackList>? = null,
//)
//
///**
// * Specifies the behavior for managing fragments in the back stack.
// */
//enum class BackstackPolicy {
//    /**
//     * Searches for an existing instance of the fragment in the stack. If found, removes all
//     * fragments above it. If not found, creates and adds a new instance to the top of the stack.
//     */
//    SINGLE_INSTANCE_CLEAR_UPPER,
//
//    /**
//     * Locates an existing instance of the fragment in the stack and moves it to the top.
//     * If no instance is found, creates a new one and adds it to the top of the stack.
//     */
//    SINGLE_INSTANCE_MOVE_UP,
//
//    /**
//     * Restricts the fragment to be added only when the back stack is empty, ensuring it can
//     * only serve as the root fragment.
//     */
//    ONLY_ROOT,
//
//    /**
//     * Adds a new instance of the fragment only if the top of the stack doesn't already
//     * contain a fragment of the same type.
//     */
//    SINGLE_TOP,
//
//    /**
//     * Adds a new instance of the fragment to the top of the stack without any restrictions
//     * or checks on existing instances.
//     */
//    ANY,
//}
//
//enum class ControlFunctions {
//    SHOW_HIDE,
//    ATTACH_DETACH,
//}