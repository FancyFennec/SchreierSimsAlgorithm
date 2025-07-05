package org.example

import org.example.Permutation.Companion.E
import java.util.*

class PermutationPuzzle(generators: Map<Permutation, String>, target: Permutation) {

    private val generators: Map<Permutation, String> = generators
    private val target: Permutation = target

    fun solve(): String {
        val startMap = mutableMapOf(E to "E")
        val targetMap = mutableMapOf(target to "T")

        val startQueue = LinkedList<Permutation>()
        val targetQueue = LinkedList<Permutation>()
        startQueue.add(E)
        targetQueue.add(target)
        var intersection: Permutation? = null
        while (startQueue.isNotEmpty() || targetQueue.isNotEmpty()) {
            val firstResult = pollFromQueue(startQueue, startMap, targetMap)
            if (firstResult.status == PollStatus.FOUND) {
                intersection = firstResult.intersection
                break
            }
            val secondResult = pollFromQueue(targetQueue, targetMap, startMap)
            if (secondResult.status == PollStatus.FOUND) {
                intersection = secondResult.intersection
                break
            }
            if (firstResult.status == PollStatus.CANCELLED || secondResult.status == PollStatus.CANCELLED) {
                break
            }
        }
        return intersection?.let {
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
        } ?: ""
    }

    private fun pollFromQueue(
        queue: LinkedList<Permutation>,
        map: MutableMap<Permutation, String>,
        otherMap: Map<Permutation, String>
    ): PollResult {
        queue.poll()?.let { p ->
            if (p in otherMap) {
                return PollResult(PollStatus.FOUND, p)
            }
            if (map[p]?.let { it.split(",").size > 8 } == true) {
                return PollResult(PollStatus.CANCELLED, p)
            }
            map[p]?.let { path ->
                generators.flatMap { (g, letter) -> listOf(g to letter, g.inv to "-$letter") }
                    .forEach { (g, letter) ->
                        val newKey = g * p
                        if (!map.containsKey(newKey)) {
                            map[newKey] = "$letter,$path"
                            queue.add(newKey)
                        }
                    }
            }
        }
        return PollResult(PollStatus.NOT_FOUND, null)
    }

    class PollResult(val status: PollStatus, val intersection: Permutation?) {}

    enum class PollStatus {
        FOUND,
        NOT_FOUND,
        CANCELLED
    }
}