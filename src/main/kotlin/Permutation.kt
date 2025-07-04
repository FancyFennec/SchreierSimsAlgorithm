package org.example

import java.util.*

@OptIn(ExperimentalUnsignedTypes::class)
class Permutation {

    val permutation:  UByteArray

    constructor(permutationString: String) {
        this.permutation = initialize(permutationString)
    }

    constructor(permutation:UByteArray) {
        this.permutation = permutation
    }

    companion object {
        private val permutationRegex = Regex("""(\((?:\d+,)*\d+\))*""")
        val E = Permutation("")
    }

    val keys : List<Int>
        get() = this.permutation.filter { k ->  k != permutation[k.toInt()] }.map { k -> (k + 1U).toInt() }

    private fun initialize(permutationString: String): UByteArray {
        require(permutationRegex.matches(permutationString)) {"permutation string incorrect: $permutationString"}

        val result = UByteArray(48) { i -> i.toUByte() }
        permutationString
            .split("(",")(",")")
            .filter(String::isNotEmpty)
            .forEach{ part ->
                val numbers = part.split(",")
                for((i, number) in numbers.withIndex()) {
                    result[number.toInt() - 1] = (numbers[(i + 1) % numbers.size].toInt() - 1).toUByte()
                }
            }
        return result
    }

    fun permute(i: Int) : Int {
        return (permutation[i -1] + 1U).toInt()
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
            val result = UByteArray(48) { i -> i.toUByte()}
            permutation.forEachIndexed { index, i -> result[i.toInt()] = index.toUByte() }
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
        return createString()
    }

    override fun hashCode(): Int {
        return permutation.contentHashCode()
    }

    override fun equals(other: Any?): Boolean {
        if(other is Permutation) {
            return other.let { this.permutation.contentEquals(it.permutation) }
        }
        return false
    }
}