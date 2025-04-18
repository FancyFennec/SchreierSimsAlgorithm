package org.example

import java.util.*
import org.graphstream.graph.implementations.SingleGraph
import org.graphstream.ui.view.Viewer

fun main() {
    val generators = mutableListOf(
//        Permutation("(1,2,3)(4,5)"),
        Permutation("(1,2)(3,4,5)")
    )

    val cayleyGraph = generateCayleyGraph(generators)

    println(cayleyGraph.size)

    drawCayleyGraph(cayleyGraph)
}

private fun generateCayleyGraph(generators: MutableList<Permutation>): MutableMap<Permutation, List<Permutation>> {
    val queue: Queue<Permutation> = LinkedList()
    val graph = mutableMapOf<Permutation, List<Permutation>>()
    queue.addAll(generators)

    while (queue.isNotEmpty()) {
        val current = queue.poll()
        if (!graph.contains(current)) {
            val targets = generators.map { current * it }.distinct()
            graph[current] = targets
            queue.addAll(targets.filter { !graph.contains(it) })
        }
    }
    return graph
}

private fun drawCayleyGraph(cayleyGraph: MutableMap<Permutation, List<Permutation>>) {
    System.setProperty("org.graphstream.ui", "swing");
    val graph = SingleGraph("Cayley Graph")
    graph.isStrict = false
    graph.setAutoCreate(true)

    cayleyGraph.keys.forEach { graph.addNode(it.toString()) }
    cayleyGraph.forEach { (k, v) ->
        v.forEach { graph.addEdge("$k-$it", k.toString(), it.toString()) }
    }

    graph.setAttribute("ui.stylesheet", "node { fill-color: #aaffff; size: 20px; }")
    val viewer: Viewer = graph.display()
    viewer.enableAutoLayout()
    viewer.enableXYZfeedback(true)
}