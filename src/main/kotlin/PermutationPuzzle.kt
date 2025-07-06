package org.example

import org.example.Permutation.Companion.E
import java.util.*

class PermutationPuzzle(generators: Map<Permutation, String>,  target: Permutation) {

    private val generators: Map<Permutation, String> = generators
    private val enhancedGenerators = generators.flatMap { (g, letter) -> listOf(g to letter, g.inv to "-$letter") }
    private val target: Permutation = target

    fun solve(): String {
        val startMap = mutableMapOf(E to SearchState("E", null))
        val targetMap = mutableMapOf(target to SearchState("T", null))

        val startQueue  = LinkedList<Permutation>()
        val targetQueue  = LinkedList<Permutation>()
        startQueue.add(E)
        targetQueue.add(target)
        var intersection: Permutation? = null
        while (startQueue.isNotEmpty() || targetQueue.isNotEmpty()) {
            val firstResult = pollFromQueue(startQueue, startMap, targetMap)
            if(firstResult.status == PollStatus.FOUND) {
                intersection = firstResult.intersection
                break
            }
            val secondResult = pollFromQueue(targetQueue, targetMap, startMap)
            if(secondResult.status == PollStatus.FOUND) {
                intersection = secondResult.intersection
                break
            }
            if(firstResult.status == PollStatus.CANCELLED || secondResult.status == PollStatus.CANCELLED) {
                break
            }
        }
        return intersection?.let {
            val pathFromStart = startMap[it]!!
                .chars
                .dropLast(1)
                .joinToString(",")
            val pathFromTarget = targetMap[it]!!
                .chars
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
        map: MutableMap<Permutation, SearchState>,
        otherMap: Map<Permutation, SearchState>
    ): PollResult {
        queue.poll()?.let { p ->
            if (p in otherMap) {
                return PollResult(PollStatus.FOUND, p)
            }
            if(map[p]?.let{ it.depth > 8} == true) {
                return PollResult(PollStatus.CANCELLED, p)
            }
            map[p]?.let { path ->
                enhancedGenerators.forEach { (g, letter) ->
                    val newKey = g * p
                    if(!map.containsKey(newKey)) {
                        map[newKey] = SearchState(letter, path)
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

    class SearchState(val char: String, val child : SearchState?){

        val depth : Long = child?.let { it.depth  + 1} ?: 1

        val chars:List<String> get()  {
            val result = mutableListOf<String>()
            var current : SearchState? = this
            while(current != null) {
                result.add(current.char)
                current = current.child
            }
            return result
        }

    }
}