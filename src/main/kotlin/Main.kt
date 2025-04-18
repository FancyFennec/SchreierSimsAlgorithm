package org.example

import java.util.*
import org.graphstream.graph.implementations.SingleGraph
import org.graphstream.ui.layout.Layout
import org.graphstream.ui.view.Viewer

fun main() {
    val E = Permutation("")
    val U = Permutation("(1,2,4,3)(23,14,6,10)(24,13,5,9)")
    val F = Permutation("(5,6,8,7)(3,13,18,12)(4,15,17,10)")
    val R = Permutation("(13,14,16,15)(4,24,20,8)(2,22,18,6)")
    val L = Permutation("(9,10,12,11)(1,5,17,21)(3,7,19,23)")

    val generators = mutableListOf(
//        U, R
//        U, F, R
        U * R / U / R,
//        U * R * R / L
    )

    val cayleyGraph = generateCayleyGraph(generators)

    println(cayleyGraph.size)

//    drawCayleyGraph(cayleyGraph)
}

private fun generateCayleyGraph(generators: List<Permutation>): Map<String, Set<String>> {
    val graph = mutableMapOf<String, Set<String>>()
    val queue  = HashQueue<String>()
    queue.addAll(generators.map(Permutation::toString))
    while (queue.isNotEmpty()) {
        val current = queue.poll()
        val targets = generators.map { Permutation(current) * it }
            .map(Permutation::toString)
            .toHashSet()
        graph[current] = targets
        queue.addAll(targets)
        if (graph.size % 100000 == 0) {
            println(graph.size)
        }
    }
    return graph
}

private fun drawCayleyGraph(cayleyGraph: Map<String, Set<String>>) {
    System.setProperty("org.graphstream.ui", "swing");
    val graph = SingleGraph("Cayley Graph")
    graph.isStrict = false
    graph.setAutoCreate(true)

    cayleyGraph.keys.forEach { graph.addNode(it) }
    cayleyGraph.forEach { (k, v) ->
        v.forEach { graph.addEdge("$k-$it", k, it) }
    }

    graph.setAttribute("ui.stylesheet", "node { fill-color: #aaffff; size: 10px; }")
    val viewer: Viewer = graph.display()
    viewer.enableAutoLayout()
    viewer.enableXYZfeedback(true)
}