package org.example

import org.example.Permutation.Companion.E
import org.graphstream.graph.implementations.SingleGraph
import org.graphstream.ui.view.Viewer
import kotlin.math.pow
import kotlin.math.sign

fun main() {
    val rubiksCube = SimpleThreeRubiksCube()
//    val pin = Permutation("(3,8,25,35,16,30)(9,46,11)(17,27,48)")
//    val solution = rubiksCube.solve(pin)
    val stabilizers = rubiksCube.stabilizersThatPermute(
        mutableListOf(
//            9,10,11,12,13,14,15,16,
//            1,2,3,17,18,19,25,26,26,46,47,48,
//            5,28

//            3, 16, 25, 8, 30, 35

//            9,10,11,12,13,14,15,16,
//            1,2,3,17,18,19,25,26,27,46,47,48,
//            5,28,
//            8,30,35

            9,17,46,
            10,47,
            11,27,48,
            13,26,
            3,16,25,
            5,28,
            8,30,35
        )
    )
    println("Group size: ${SchreierSims(stabilizers).size}")
    val cayleyGraph = CayleyGraph(stabilizers)
    cayleyGraph.graph.keys
        .map{ rubiksCube.solve(it) }
        .filter { it.isNotEmpty() }
        .sortedBy{ it.length}
        .forEach { g ->
            println("Stabilizer ${rubiksCube.permute(g)}")
            println("Path: $g")
        }
//        .map{ rubiksCube.solve(Permutation(it)) }
//    stabilizers.forEach { g ->
//        println("Stabilizer $g")
//        println("Path: ${rubiksCube.solve(g)}")
//    }

}

