package org.example

import java.util.*

class Permutation {
    
    private val permutation: Map<Int, Int>
    private val permutationString: String
    private val hash: Int

    constructor(permutationString: String) {
        this.permutation = initialize(permutationString)
        this.permutationString = toString(permutation)
        this.hash = this.permutationString.hashCode()
    }

    constructor(permutation: Map<Int, Int>) {
        this.permutation = permutation
        this.permutationString = toString(permutation)
        this.hash = this.permutationString.hashCode()
    }

    companion object {
        private val permutationRegex = Regex("""(\((?:\d+,)*\d+\))*""")
    }

    private fun initialize(permutationString: String): Map<Int, Int> {
        if(!permutationRegex.matches(permutationString)) {
            throw RuntimeException("permutation string incorrect: $permutationString")
        }
        val result = mutableMapOf<Int, Int>()
        permutationString
            .split("(",")(",")")
            .filter(String::isNotEmpty)
            .forEach{ part ->
                val numbers = part.split(",")
                for((i, number) in numbers.withIndex()) {
                    result[number.toInt()] = numbers[(i + 1) % numbers.size].toInt()
                }
            }
        return result
    }

    fun permute(i: Int) : Int {
        return permutation.getOrElse(i) { i }
    }

    operator fun times(other: Permutation): Permutation {
        val keys = (permutation.keys + other.permutation.keys)
        if (keys.isEmpty()) {
            return Permutation("")
        }
        return Permutation((keys.min()..keys.max()).associateWith { other.permute(permute(it)) })
    }

    operator fun div(other: Permutation): Permutation {
        return times(other.inv())
    }

    private fun inv(): Permutation {
        return Permutation(permutation.keys.associateBy(::permute))
    }

    private fun toString(permutation: Map<Int, Int>): String {
        val builder = StringBuilder()
        val numberQueue: Queue<Int> = LinkedList()
        numberQueue.addAll(permutation.keys.sorted())
        while(numberQueue.isNotEmpty()) {
            val first = numberQueue.poll()
            if(permute(first) == first) {
                continue
            }
            builder.append("(")
            builder.append(first)
            var current = permute(first)
            while(current != first){
                numberQueue.remove(current)
                builder.append(",")
                builder.append(current)
                current = permute(current)
            }
            builder.append(")")
        }
        return builder.toString()
    }

    override fun toString(): String {
        return permutationString
    }

    override fun hashCode(): Int {
        return hash
    }

    override fun equals(other: Any?): Boolean {
        return other?.let { this.hashCode() == it.hashCode() }?:false
    }
}