package daxo.the.anikat.tests.navigation_test

import android.util.Log
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import daxo.the.anikat.R
import daxo.the.anikat.tests.navigation_test.FragmentRules.BackstackPolicy.*
import kotlin.reflect.KClass

class FragmentsNavigator(
    private val viewModelProvider: ViewModelProvider,
    private val supportFragmentManager: FragmentManager,
    private var ge: List<FragmentsNavigatorElement>,
) {
    private val backstack = FragmentQueue<Fragment>()
    private val fragmentCache = HashMap<KClass<out Fragment>, Fragment>()

    private val tagViewModelCache: MutableMap<String, ViewModel> = mutableMapOf()
    private val classViewModelCache: MutableMap<KClass<out ViewModel>, ViewModel> = mutableMapOf()

    private val TAG = "FragmentsNavigator"

    private fun findRbK(key: Int): FragmentRules? = ge.find { it.id == key }?.fragmentRules
    private fun findCbK(key: Int): KClass<out Fragment>? = ge.find { it.id == key }?.klass
    private fun findFbK(key: Int): (() -> Fragment)? = ge.find { it.id == key }?.fragmentFactory
    private fun findKbC(klass: Fragment): Int? = ge.find { it.klass.isInstance(klass) }?.id

    fun navigateTo(@IdRes key: Int) {
        val rules =
            findRbK(key) ?: keyNotFound(key)
        val type =
            findCbK(key) ?: keyNotFound(key)
        val factory =
            findFbK(key) ?: keyNotFound(key)

        when (rules.backstackPolicy) {
            SINGLE_INSTANCE_CACHING_ANY -> policySINGLE_INSTANCE_CACHING(type, factory)

            SINGLE_INSTANCE_CACHING_SINGLE_TOP -> if (!type.isInstance(backstack.last())) {
                policySINGLE_INSTANCE_CACHING(type, factory)
            }

            SINGLE_INSTANCE_CLEAR_UPPER -> policySINGLE_INSTANCE_CLEAR_UPPER(type, key, factory)

            SINGLE_INSTANCE_MOVE_UP -> policySINGLE_INSTANCE_MOVE_UP(type, key, factory)

            SINGLE_TOP -> policySINGLE_TOP(type, key, factory)

            ANY -> policyANY(key, factory)
        }
    }

    private fun keyNotFound(key: Int): Nothing {
        throw IllegalStateException("key $key not found")
    }

    private fun classNotFound(klass: Class<out Fragment>): Nothing {
        throw IllegalStateException("class $klass not found")
    }

    private fun viewModelProviderNotFound(
        klass: KClass<out ViewModel>, tag: String? = null
    ): Nothing {
        throw IllegalStateException(
            "viewModelProvider for $klass not found ${if (tag != null) "(tag: $tag)" else ""}"
        )
    }

    private fun policySINGLE_INSTANCE_CACHING(
        type: KClass<out Fragment>,
        factory: () -> Fragment
    ) {
        val finded = fragmentCache[type]
        if (finded != null) {
            navigate(finded)
            backstack.enqueue(finded)
            Log.d(TAG, "policySINGLE_INSTANCE_CACHING: finded: $type $finded")
        } else {
            val fragment = factory()
            fragmentCache[type] = fragment
            navigate(fragment)
            backstack.enqueue(fragment)
            Log.d(TAG, "policySINGLE_INSTANCE_CACHING: not finded $type $fragment")
        }
    }

    private fun policySINGLE_INSTANCE_CLEAR_UPPER(
        type: KClass<out Fragment>,
        key: Int,
        factory: () -> Fragment
    ) {
        val finded = backstack.findByClass(type)
        if (finded == null) {
            createNewAndNavigate(key, factory)
            Log.d(TAG, "policySINGLE_INSTANCE_CLEAR_UPPER: not finded $key")
        } else {
            backstack.dequeueTo(finded)
            navigate(finded)
            Log.d(TAG, "policySINGLE_INSTANCE_CLEAR_UPPER: finded $key $finded")
        }
    }

    private fun policySINGLE_INSTANCE_MOVE_UP(
        type: KClass<out Fragment>,
        key: Int,
        factory: () -> Fragment
    ) {
        val finded = backstack.findByClass(type)
        if (finded == null) {
            createNewAndNavigate(key, factory)
        } else {
            backstack.moveToEnd(finded)
            navigate(finded)
        }
    }

    private fun policySINGLE_TOP(
        type: KClass<out Fragment>,
        key: Int,
        factory: () -> Fragment
    ) {
        if (!type.isInstance(backstack.last())) {
            createNewAndNavigate(key, factory)
        }
    }

    private fun policyANY(key: Int, factory: () -> Fragment) {
        createNewAndNavigate(key, factory)
    }


    private fun navigate(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.navHostFragmentContainerView,
                fragment
            ).commit()
    }

    private fun createNewAndNavigate(key: Int, factory: () -> Fragment): Fragment {
        val fragment = factory()
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.navHostFragmentContainerView,
                fragment
            ).commit()
        backstack.enqueue(fragment)
        return fragment
    }

    fun onBackPressed(): Boolean {
        val current = backstack.dequeue() ?: return true
        do {
            val backTo = backstack.last() ?: return true
            val id = findKbC(current) ?: classNotFound(current::class.java)
            val rules = findRbK(id) ?: keyNotFound(id)
            if (rules.allowBackstackTo.contains(backTo::class)) {
                navigate(backTo)
                return false
            } else {
                backstack.dequeue()
            }
        } while (true)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : ViewModel> getVM(klass: KClass<T>): T {
        val vm = classViewModelCache[klass]
            ?: viewModelProvider[klass]?.invoke()
            ?: viewModelProviderNotFound(klass)
        classViewModelCache[klass] = vm
        return vm as T
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : ViewModel> getVM(klass: KClass<T>, tag: String): T {
        val vm = tagViewModelCache[tag]
            ?: viewModelProvider[klass]?.invoke()
            ?: viewModelProviderNotFound(klass)
        tagViewModelCache[tag] = vm
        return vm as T
    }
}