package org.example

import org.example.Permutation.Companion.E

class TwoRubiksCube {

    companion object {
        val U = Permutation("(5,6,8,7)(23,14,2,10)(24,13,1,9)")
        val F = Permutation("(1,2,4,3)(7,13,18,12)(8,15,17,10)")
        val L = Permutation("(9,10,12,11)(5,1,17,21)(7,3,19,23)")
        val R = Permutation("(13,14,16,15)(8,24,20,4)(6,22,18,2)")
    }

    private val dictionary = mapOf(
        F to "F",
        L to "L",
        R to "R",
        U to "U",
        E to "E",
    )

    fun solve(p: Permutation): String {
        return PermutationPuzzle(dictionary, p).solve()
    }

    fun permute(ps: String): Permutation {
        return ps.split(',').map {
            dictionary.entries.find { entry -> entry.value == it }?.key ?:
            dictionary.entries.find { entry -> entry.value == it.removePrefix("-") }?.key?.inv
        }.fold(E){a,b -> a*b!!}
    }

    val generators : List<Permutation>
        get() = dictionary.keys.toList()

    val size : Long
        get() = SchreierSims(generators).size

    fun stabilizersThatPermute(ps: List<Int>): List<Permutation> {
        val stabilizers = generators.flatMap { it.keys }.distinct().filter { it !in ps }
        return stabilizersOf(stabilizers)
    }

    fun stabilizersOf(stabilizers: List<Int>): List<Permutation> {
        var current = SchreierSims(generators, stabilizers)
        while(current.stabilizer != null) {
            current = current.stabilizer
        }
        return current.stabilizerGenerators
    }
}