package org.example

import org.example.Permutation.Companion.E
import java.math.BigInteger

interface Cube {

    val dictionary: Map<Permutation, String>

    fun solve(p: Permutation): String {
        return PermutationPuzzle(dictionary, p).solve()
    }

    fun permute(ps: String): Permutation {
        return ps.split(',').map {
            dictionary.entries.find { entry -> entry.value == it }?.key
                ?: dictionary.entries.find { entry -> entry.value == it.removePrefix("-") }?.key?.inv
        }.fold(E) { a, b -> a * b!! }
    }

    val generators: List<Permutation>
        get() = dictionary.keys.toList()

    val size: BigInteger
        get() = SchreierSimsAlgorithm(generators).size

    fun stabilizersOf(stabilizers: List<Int>): List<Permutation> {
        var current = SchreierSimsAlgorithm(generators, stabilizers)
        while (current.child != null) {
            current = current.child
        }
        return current.stabilizerGenerators
    }

    fun stabilizersThatPermute(ps: List<Int>): List<Permutation> {
        val stabilizers = generators.flatMap { it.keys }.distinct().filter { it !in ps }
        return stabilizersOf(stabilizers)
    }
}