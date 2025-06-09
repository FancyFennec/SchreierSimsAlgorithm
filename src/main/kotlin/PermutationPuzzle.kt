package org.example

import org.example.Permutation.Companion.E
import java.util.LinkedList

class PermutationPuzzle(generators: Map<Permutation, String>,  target: Permutation) {

    private val generators: Map<Permutation, String> = generators
    private val target: Permutation = target

    fun solve(): String {
        val startMap = mutableMapOf(E to "E")
        val targetMap = mutableMapOf(target to "T")

        val startQueue  = LinkedList<Permutation>()
        val targetQueue  = LinkedList<Permutation>()
        startQueue.add(E)
        targetQueue.add(target)
        var intersection: Permutation? = null
        while (intersection == null && (startQueue.isNotEmpty() || targetQueue.isNotEmpty())) {
            intersection = pollFromQueue(startQueue, startMap, targetMap)
                ?: pollFromQueue(targetQueue, targetMap, startMap)
        }
        return intersection?.let { it ->
            val pathFromStart = startMap[it]!!.let{ it.substring(0, it.length - 2) }
            val pathFromTarget = targetMap[it]!!.let{ it.substring(0, it.length - 2) }
                .split(",")
                .reversed()
                .joinToString(",") { letter ->
                    if (letter.startsWith('-')) letter.substring(1) else "-$letter"
                }
            "${pathFromTarget},${pathFromStart}"
        }?: ""
    }

    private fun pollFromQueue(
        queue: LinkedList<Permutation>,
        map: MutableMap<Permutation, String>,
        otherMap: Map<Permutation, String>
    ): Permutation? {
        queue.poll()?.let { p ->
            if (p in otherMap) {
                return p
            }
            map[p]?.let { path ->
                generators.flatMap { (g, letter) -> listOf(g to letter, g.inv to "-$letter") }
                    .forEach { (g, letter) ->
                        map[g * p] = "$letter,$path"
                        queue.add(g * p)
                    }
            }
        }
        return null
    }
}