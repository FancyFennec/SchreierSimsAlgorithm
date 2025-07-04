package org.example

import org.example.Permutation.Companion.E

class PocketCube(override val dictionary: Map<Permutation, String>) : Cube {

    companion object {
        val F = Permutation("(1,2,4,3)(7,13,18,12)(8,15,17,10)")
        val B = Permutation("(17,18,19,20)(3,15,22,11)(4,16,21,12)")
        val L = Permutation("(9,10,12,11)(5,1,17,21)(7,3,19,23)")
        val R = Permutation("(13,14,16,15)(8,24,20,4)(6,22,18,2)")
        val U = Permutation("(5,6,8,7)(23,14,2,10)(24,13,1,9)")
        val D = Permutation("(21,22,24,23)(19,16,6,9)(20,14,5,11)")
    }

    constructor() : this(  mapOf(
        F to "F",
        B to "B",
        L to "L",
        R to "R",
        U to "U",
        D to "D",
        E to "E"
    ))

    class Builder {

        private val dictionary = mutableMapOf(E to "E")

        fun withF() : Builder {
            dictionary[F] = "F"
            return this
        }

        fun withB() : Builder {
            dictionary[B] = "B"
            return this
        }

        fun withL() : Builder {
            dictionary[L] = "L"
            return this
        }

        fun withR() : Builder {
            dictionary[R] = "R"
            return this
        }

        fun withU() : Builder {
            dictionary[U] = "U"
            return this
        }

        fun withD() : Builder {
            dictionary[D] = "D"
            return this
        }

        fun build() : PocketCube {
            return PocketCube(dictionary)
        }
    }
}