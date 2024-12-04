package daxo.the.navigation

import androidx.annotation.IdRes

data class BackstackList(val stacks: Map<String, BackStackInfo>)
data class BackStackInfo(val key: String, @IdRes val rootId: Int)