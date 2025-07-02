package org.example

import org.example.Permutation.Companion.E

class PocketCube(override val dictionary: Map<Permutation, String>) : Group {

    companion object {
        val U = Permutation("(5,6,8,7)(23,14,2,10)(24,13,1,9)")
        val F = Permutation("(1,2,4,3)(7,13,18,12)(8,15,17,10)")
        val L = Permutation("(9,10,12,11)(5,1,17,21)(7,3,19,23)")
        val R = Permutation("(13,14,16,15)(8,24,20,4)(6,22,18,2)")
    }

    constructor() : this(  mapOf(
        U to "U",
        F to "F",
        L to "L",
        R to "R",
        E to "E"
    ))

    class Builder {

        private val dictionary = mutableMapOf(E to "E")

        fun withU() : Builder {
            dictionary[U] = "U"
            return this
        }

        fun withF() : Builder {
            dictionary[F] = "F"
            return this
        }

        fun withL() : Builder {
            dictionary[L] = "R"
            return this
        }

        fun withR() : Builder {
            dictionary[R] = "R"
            return this
        }

        fun build() : RubiksCube {
            return RubiksCube(dictionary)
        }
    }
}