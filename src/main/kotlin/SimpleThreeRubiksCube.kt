package org.example

import org.example.Permutation.Companion.E
import java.math.BigInteger

class SimpleThreeRubiksCube {

    companion object {
        val F = Permutation("(1,3,8,6)(2,5,7,4)(14,25,35,24)(15,28,34,21)(16,30,33,19)")
//        val L = Permutation("(17,19,24,22)(18,21,23,20)(9,1,33,41)(12,4,36,44)(14,6,38,46)")
        val R = Permutation("(25,27,32,30)(26,29,31,28)(16,48,40,8)(13,45,37,5)(11,43,35,3)")
        val U = Permutation("(9,11,16,14)(10,13,15,12)(46,27,3,19)(47,26,2,18)(48,25,1,17)")
    }

    private val dictionary = mapOf(
        F to "F",
//        L to "L",
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

    val size : BigInteger
        get() = SchreierSims(generators).size

    fun stabilizersThatPermute(ps: List<Int>): List<Permutation> {
        val stabilizers = generators.flatMap { it.keys }.distinct().filter { it !in ps }

        var current = SchreierSims(generators, stabilizers)
        while(current.stabilizer != null) {
            current = current.stabilizer
        }
        return current.stabilizerGenerators
    }
}