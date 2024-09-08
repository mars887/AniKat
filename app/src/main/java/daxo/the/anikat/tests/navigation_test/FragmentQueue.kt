package daxo.the.anikat.tests.navigation_test

import kotlin.reflect.KClass

class FragmentQueue<T> {
    private val items: MutableList<T> = mutableListOf()

    fun enqueue(element: T) {
        items.add(element)
    }

    fun dequeue(): T? {
        return if (items.isNotEmpty()) {
            val last = items.last()
            items.removeLast()
            last
        } else null
    }

    fun removeAt(index: Int): T? {
        return if (index in items.indices) items.removeAt(index) else null
    }

    fun remove(element: T): T? {
        val item = items.find { it == element } ?: return null
        items.remove(element)
        return item
    }

    fun moveToEnd(index: Int): Boolean {
        val moved = removeAt(index) ?: return false
        items.add(moved)
        return true
    }

    fun moveToEnd(element: T): Boolean {
        val moved = remove(element) ?: return false
        items.add(moved)
        return true
    }

    fun contains(element: T): Boolean = items.contains(element)
    fun get(index: Int): T? = items.getOrNull(index)
    fun last(): T? = items.lastOrNull()
    fun findByClass(clas: KClass<*>): T? = items.find { clas.isInstance(it) }

    override fun toString(): String {
        return items.toString()
    }

    fun dequeueTo(element: T) {
        while (items.last() != element) items.removeLastOrNull()
    }
}