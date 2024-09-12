package daxo.the.anikat.tests.navigation_test

import androidx.fragment.app.Fragment
import kotlin.reflect.KClass

data class FragmentRules(
    val backstackPolicy: BackstackPolicy,
    val allowBackstackTo: MutableSet<KClass<out Fragment>>,
) {
    enum class BackstackPolicy {
        SINGLE_INSTANCE_CACHING_SINGLE_TOP,
        SINGLE_INSTANCE_CACHING_ANY,
        SINGLE_INSTANCE_CLEAR_UPPER,
        SINGLE_INSTANCE_MOVE_UP,
        SINGLE_TOP,
        ANY
    }
}