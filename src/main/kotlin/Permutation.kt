package org.example

import java.util.*

class Permutation {

    public val permutation:  Array<Int>
    private val permutationString: String
    private val hash: Int

    constructor(permutationString: String) {
        this.permutation = initialize(permutationString)
        this.permutationString = createString()
        this.hash = this.permutation.hashCode()
    }

    constructor(permutation:Array<Int>) {
        this.permutation = permutation
        this.permutationString = createString()
        this.hash = this.permutationString.hashCode()
    }

    companion object {
        private val permutationRegex = Regex("""(\((?:\d+,)*\d+\))*""")
        val E = Permutation("")
    }

    val keys : List<Int>
        get() = this.permutation.filter { k ->  k != permutation[k] }.map { k -> k + 1 }

    private fun initialize(permutationString: String): Array<Int> {
        if(!permutationRegex.matches(permutationString)) {
            throw RuntimeException("permutation string incorrect: $permutationString")
        }
        val result = Array(48) { i -> i }
        permutationString
            .split("(",")(",")")
            .filter(String::isNotEmpty)
            .forEach{ part ->
                val numbers = part.split(",")
                for((i, number) in numbers.withIndex()) {
                    result[number.toInt() - 1] = numbers[(i + 1) % numbers.size].toInt() - 1
                }
            }
        return result
    }

    fun permute(i: Int) : Int {
        return permutation[i -1] + 1
    }

    operator fun times(other: Permutation): Permutation {
        val result = Array(48) { i -> i }
        result.forEachIndexed { index, i -> result[index] = this.permutation[other.permutation[index]]}
        return Permutation(result)
    }

    operator fun times(other: Int): Int {
        return this.permutation[other - 1] + 1
    }

    operator fun div(other: Permutation): Permutation {
        return times(other.inv)
    }

    val inv: Permutation
        get() {
            val result = Array(48) { i -> i }
            permutation.forEachIndexed { index, i -> result[i] = index }
            return Permutation(result)
        }

    val isIdentity: Boolean
        get() { return toString().isBlank() }

    private fun createString(): String {
        val builder = StringBuilder()
        val numberQueue: Queue<Int> = LinkedList()
        numberQueue.addAll((1..42))
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
        if(other is Permutation) {
            return other.let { this.permutation.contentEquals(it.permutation) }
        }
        return false
    }
}