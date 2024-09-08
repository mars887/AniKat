package daxo.the.anikat.tests.navigation_test

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import kotlin.reflect.KClass

data class FragmentsNavigatorElement(
    @IdRes val id: Int,
    val klass: KClass<out Fragment>,
    val fragmentRules: FragmentRules,
    val fragmentFactory: () -> Fragment,
    val viewModelKlass: KClass<out ViewModel>? = null
)