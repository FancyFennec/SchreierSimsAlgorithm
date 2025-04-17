package org.example

import java.util.*

fun main() {
//    val neutral = Permutation("(1)(2)(3)(4)(5)")
//    val gen1 = Permutation("(1,2,3)(4,5)")
//    val gen2 = Permutation("(1,2)(3,4,5)")
    val neutral = Permutation("(1)(2)(3)(4)")
    val gen1 = Permutation("(1)(2)(3,4)")
    val gen2 = Permutation("(1,3,2,4)")
    val gen3 = Permutation("(1,4,2,3)")
    val generators = mutableListOf(gen1, gen2, gen3)

    val queue: Queue<Permutation> = LinkedList()
    val graph = mutableMapOf<Permutation, List<Permutation>>()
    queue.add(neutral)

    while (queue.isNotEmpty()) {
        val current = queue.poll()
        if(!graph.contains(current)) {
            val targets = generators.map { current * it }.distinct()
            graph[current] = targets
            queue.addAll(targets.filter { !graph.contains(it) })
        }
    }

    println(graph.size)
}