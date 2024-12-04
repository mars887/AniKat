package daxo.the.navigation.simpletest

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import dagger.assisted.AssistedFactory
import daxo.the.navigation.BackstackList

@AssistedFactory
interface NavController2Factory {
    fun create(
        @IdRes host: Int,
        defaultBackstackKey: String,
        backstackList: Map<String,Int>,
        fragmentManager: FragmentManager,
        navController: NavController
    ): NavController2
}