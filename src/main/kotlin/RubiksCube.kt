package org.example

import org.example.Permutation.Companion.E

class RubiksCube(override val dictionary: Map<Permutation, String>) : Group {

    companion object {
        val F = Permutation("(1,3,8,6)(2,5,7,4)(14,25,35,24)(15,28,34,21)(16,30,33,19)")
        val B = Permutation("(41,43,48,46)(42,45,47,44)(38,32,11,17)(39,29,10,20)(40,27,9,22)")
        val L = Permutation("(17,19,24,22)(18,21,23,20)(9,1,33,41)(12,4,36,44)(14,6,38,46)")
        val R = Permutation("(25,27,32,30)(26,29,31,28)(16,48,40,8)(13,45,37,5)(11,43,35,3)")
        val U = Permutation("(9,11,16,14)(10,13,15,12)(46,27,3,19)(47,26,2,18)(48,25,1,17)")
        val D = Permutation("(33,35,40,38)(34,37,39,36)(6,30,43,22)(7,31,42,23)(8,32,41,24)")
    }

    constructor() : this(  mapOf(
    F to "F",
    B to "B",
    L to "L",
    R to "R",
    U to "U",
    D to "D",
    E to "E",
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

        fun build() : RubiksCube {
            return RubiksCube(dictionary)
        }
    }

}