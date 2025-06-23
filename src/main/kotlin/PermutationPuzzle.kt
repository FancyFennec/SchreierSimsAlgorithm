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
        val startTimestamp = System.currentTimeMillis()
        while (intersection == null && (startQueue.isNotEmpty() || targetQueue.isNotEmpty())) {
            intersection = pollFromQueue(startQueue, startMap, targetMap)
                ?: pollFromQueue(targetQueue, targetMap, startMap)
            if(System.currentTimeMillis() - startTimestamp > 100000) {
                return ""
            }
        }
        return intersection?.let { it ->
            val pathFromStart = startMap[it]!!
                .split(",")
                .dropLast(1)
                .joinToString(",")
            val pathFromTarget = targetMap[it]!!
                .split(",")
                .dropLast(1)
                .reversed()
                .joinToString(",") { letter ->
                    if (letter.startsWith('-')) letter.substring(1) else "-$letter"
                }
            listOf(pathFromTarget, pathFromStart)
                .filter(String::isNotBlank)
                .joinToString(",")
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
                        val newKey = g * p
                        if(!map.containsKey(newKey)) {
                            map[newKey] = "$letter,$path"
                            queue.add(newKey)
                        }
                    }
            }
        }
        return null
    }
}