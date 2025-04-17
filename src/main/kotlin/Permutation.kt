package org.example

import java.util.*

class Permutation(private val permutationString: String) {

    companion object {
        private val permutationRegex = Regex("""(\((?:\d+,)*\d\))+""")
    }

    private val permutation: Map<Int, Int> = initialize()

    private fun initialize(): Map<Int, Int> {
        assert(permutationRegex.matches(permutationString))
        val result = mutableMapOf<Int, Int>()
        permutationString
            .split("(",")(",")")
            .filter(String::isNotEmpty)
            .forEach{ part ->
                val numbers = part.split(",")
                if(numbers.size == 1){
                    val first = numbers[0];
                    result[first.toInt()] = first.toInt()
                } else {
                    for((i, number) in numbers.withIndex()) {
                        result[number.toInt()] = numbers[(i + 1) % numbers.size].toInt()
                    }
                }
            }
        return result
    }

    operator fun times(other: Permutation): Permutation {
        val result = mutableMapOf<Int, Int>()
        (1..permutation.size).forEach {
            result[it] = other.permutation[permutation[it]]!!
        }
        return Permutation(toString(result))
    }

    override fun toString(): String {
        return toString(permutation)
    }

    private fun toString(permutation: Map<Int, Int>): String {
        val builder = StringBuilder()
        val numberQueue: Queue<Int> = LinkedList()
        (1..permutation.size).forEach {
            numberQueue.add(it)
        }
        while(numberQueue.isNotEmpty()) {
            builder.append("(")
            val first = numberQueue.poll()
            builder.append(first)
            var current = permutation[first]
            while(current != first){
                numberQueue.remove(current)
                builder.append(",")
                builder.append(current)
                current = permutation[current]
            }
            builder.append(")")
        }
        return builder.toString()
    }

    override fun hashCode(): Int {
        return toString().hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return other?.let { this.toString() == it.toString() }?:false
    }
}