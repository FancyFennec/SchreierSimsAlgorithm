package org.example

import java.util.*

@OptIn(ExperimentalUnsignedTypes::class)
class Permutation {

    val permutation: UByteArray

    constructor(permutationString: String) {
        this.permutation = initialize(permutationString)
    }

    constructor(permutation: UByteArray) {
        this.permutation = permutation
    }

    companion object {
        private val permutationRegex = Regex("""(\((?:\d+,)*\d+\))*""")
        val E = Permutation("")
    }

    val keys: List<Int>
        get() = this.permutation.filter { k -> k != permutation[k.toInt()] }
            .map { k -> (k + 1U).toInt() }
            .sorted()

    private fun initialize(permutationString: String): UByteArray {
        require(permutationRegex.matches(permutationString)) { "permutation string incorrect: $permutationString" }

        val result = UByteArray(48) { i -> i.toUByte() }
        permutationString
            .split("(", ")(", ")")
            .filter(String::isNotEmpty)
            .forEach { part ->
                val numbers = part.split(",")
                for ((i, number) in numbers.withIndex()) {
                    result[number.toInt() - 1] = (numbers[(i + 1) % numbers.size].toInt() - 1).toUByte()
                }
            }
        return result
    }

    operator fun times(other: Permutation): Permutation {
        val result = UByteArray(48) { i -> i.toUByte() }
        result.forEachIndexed { index, _ -> result[index] = this.permutation[other.permutation[index].toInt()] }
        return Permutation(result)
    }

    operator fun times(other: Int): Int {
        return (this.permutation[other - 1] + 1U).toInt()
    }

    operator fun div(other: Permutation): Permutation {
        return times(other.inv)
    }

    val inv: Permutation
        get() {
            val result = UByteArray(48) { i -> i.toUByte() }
            permutation.forEachIndexed { index, i -> result[i.toInt()] = index.toUByte() }
            return Permutation(result)
        }

    val isIdentity: Boolean get() = equals(E)

    private fun createString(): String {
        val builder = StringBuilder()
        val numberQueue: Queue<Int> = LinkedList()
        numberQueue.addAll(keys)
        while (numberQueue.isNotEmpty()) {
            val first = numberQueue.poll()
            builder.append("(")
            builder.append(first)
            var current = this * first
            while (current != first) {
                numberQueue.remove(current)
                builder.append(",")
                builder.append(current)
                current = this * current
            }
            builder.append(")")
        }
        return builder.toString()
    }

    override fun toString(): String {
        return createString()
    }

    override fun hashCode(): Int {
        return permutation.contentHashCode()
    }

    override fun equals(other: Any?): Boolean {
        return if (other is Permutation) permutation.contentEquals(other.permutation) else false
    }
}