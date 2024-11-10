package daxo.the.navigation

//class FragmentsController(
//    defaultBackstackKey: BackstackList,                                                                                 // startup backstack key
//    navigationBackstacks: SortedSet<NavigationBackstackConfig>,                                                         // navigation configs
//    fragmentsData: HashSet<FragmentConfig>,                                                                             // fragment configs
//    private val fragmentsProvider: FragmentsProvider,
//    private val fragmentManager: FragmentManager,                                                                       // fragment manager
//) {
//
//    private var currentBackstackKey: MutableWrapper<BackstackList> = MutableWrapper(defaultBackstackKey)
//
//    private val backstacksConfigs: EnumMap<BackstackList, NavigationBackstackConfig>
//
//    private val fragmentsConfigs: HashMap<Int, FragmentConfig>
//
//    private val backstackController: BackstackController
//
//    init {
//        require(navigationBackstacks.isNotEmpty()) { "Navigation backstack should not be empty" }
//        require(fragmentsData.isNotEmpty()) { "Fragments data should not be empty" }
//
//                                                                                                                        /* -- backstack configuration -- */
//
//        navigationBackstacks
//            .groupBy { it.backstackKey }
//            .filter { it.value.size > 1 }
//            .onEach { (key, configs) ->
//                Log.w(TAG, "backstack configuration: backstackKey: $key duplicates ${configs.size} times")
//            }.takeIf { it.isNotEmpty() }?.let {
//                throw IllegalStateException("Duplicate backstack keys: ${it.keys}")
//            }
//
//        backstacksConfigs = EnumMap(
//            navigationBackstacks.associateBy(
//                { it.backstackKey }, { it }
//            )
//        )
//
//        backstacksConfigs.forEach { (key, config) ->
//            if (config == null) Log.w(TAG, "backstack configuration: config for key $key not found")
//        }
//
//        backstacksConfigs[currentBackstackKey.value]
//            ?: throw IllegalStateException("current backstack key not found in navigation backstacks: $currentBackstackKey")
//
//                                                                                                                        /* -- fragments configuration -- */
//
//        val duplicateIds = fragmentsData
//            .groupBy { it.fragmentId }
//            .filter { it.value.size > 1 }
//            .onEach { (key, configs) ->
//                Log.w(TAG, "fragments configuration: fragmentId: $key duplicates ${configs.size} times")
//            }.takeIf { it.isNotEmpty() }
//
//        val duplicateClasses = fragmentsData
//            .groupBy { it.fragmentClass }
//            .filter { it.value.size > 1 }
//            .onEach { (key, configs) ->
//                Log.w(TAG, "fragments configuration: class: $key duplicates ${configs.size} times")
//            }.takeIf { it.isNotEmpty() }
//
//        duplicateIds?.let {
//            throw IllegalStateException("Duplicate fragment ids: ${it.keys}")
//        }
//
//        duplicateClasses?.let {
//            throw IllegalStateException("Duplicate fragment classes: ${it.keys}")
//        }
//
//        fragmentsConfigs = HashMap(
//            fragmentsData.associateBy(
//                { it.fragmentId }, { it }
//            )
//        )
//
//        fragmentsConfigs.keys.forEach {
//            if (!fragmentsProvider.containsKey(it)) throw IllegalStateException("Fragment factory for fragmentId: $it not found")
//        }
//
//                                                                                                                        /* -- initialize backstacks -- */
//
//
//                                                                                                                        /* -- initialize backstack controller -- */
//
//        backstackController = BackstackController(
//            fragmentManager,
//            currentBackstackKey,
//            backstacksConfigs,
//            fragmentsConfigs,
//            fragmentsProvider
//        )
//    }
//
//    companion object {
//        private const val TAG = "FragmentsController"
//    }
//}
//data class MutableWrapper<T>(var value: T)