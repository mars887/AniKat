package daxo.apollo

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QueryTimeController @Inject constructor(){
    private var last = 0L
    fun checkTime(): Boolean {
        if (System.currentTimeMillis() - last < QUERY_DELAY) return true else {
            last = System.currentTimeMillis()
            return false
        }
    }

    companion object {
        const val QUERY_DELAY = 500
    }
}