package org.example

import org.example.Permutation.Companion.E

class TwoRubiksCube {

    companion object {
        val U = Permutation("(1,2,4,3)(23,14,6,10)(24,13,5,9)")
        val F = Permutation("(5,6,8,7)(3,13,18,12)(4,15,17,10)")
        val R = Permutation("(13,14,16,15)(4,24,20,8)(2,22,18,6)")
        val L = Permutation("(9,10,12,11)(1,5,17,21)(3,7,19,23)")
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