package org.example

import java.util.*
import kotlin.collections.HashSet

class HashQueue<T> : Queue<T> {
    private val set = HashSet<Int>()
    private val queue = LinkedList<T>()

    override fun add(element: T): Boolean {
        return if (set.add(element.hashCode())) {
            queue.add(element)
            true
        } else {
            false // Element already exists in the set, not added
        }
    }

    // Remove and return the first element from the queue
    override fun poll(): T {
        val element = queue.poll()
        return element
    }

    override fun element(): T {
        TODO("Not yet implemented")
    }

    // Peek at the first element without removing it
    override fun peek(): T {
        return queue.peek()
    }

    override val size: Int
        get() = queue.size

    override fun contains(element: T): Boolean {
        return set.contains(element.hashCode())
    }

    override fun addAll(elements: Collection<T>): Boolean {
        val newElements = elements.filter { set.add(it.hashCode()) }
        return queue.addAll(newElements)
    }

    override fun clear() {
        set.clear()
        queue.clear()
    }

    override fun iterator(): MutableIterator<T> {
        TODO("Not yet implemented")
    }

    override fun remove(): T {
        TODO("Not yet implemented")
    }

    override fun containsAll(elements: Collection<T>): Boolean {
        return set.containsAll(elements.map{it.hashCode()}.toSet())
    }

    override fun retainAll(elements: Collection<T>): Boolean {
        TODO("Not yet implemented")
    }

    override fun removeAll(elements: Collection<T>): Boolean {
        TODO("Not yet implemented")
    }

    override fun remove(element: T): Boolean {
        TODO("Not yet implemented")
    }

    override fun isEmpty(): Boolean {
        return queue.isEmpty()
    }

    override fun offer(e: T): Boolean {
        TODO("Not yet implemented")
    }
}