package daxo.the.anikat.tests.navigation_test

import androidx.lifecycle.ViewModel
import kotlin.reflect.KClass

class ViewModelProvider(
    private val map: Map<KClass<out ViewModel>, () -> ViewModel>
) {
    @Suppress("UNCHECKED_CAST")
    operator fun <T : ViewModel> get(klass: KClass<T>): (() -> T)? {
        return map[klass]?.let { it as (() -> T) }
    }
}