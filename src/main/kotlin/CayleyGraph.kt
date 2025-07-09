package org.example

import org.graphstream.graph.implementations.SingleGraph
import org.graphstream.ui.view.Viewer

class CayleyGraph {

    val generators: List<Permutation>
    val graph: Map<Permutation, Set<Permutation>>

    constructor(generators: List<Permutation>) {
        this.generators = generators.flatMap { mutableListOf(it, it.inv) }.distinct()
        this.graph = generate()
    }

    private fun generate(): Map<Permutation, Set<Permutation>> {
        val graph = mutableMapOf<Permutation, Set<Permutation>>()
        val queue = HashQueue<Permutation>()
        queue.addAll(generators)
        while (queue.isNotEmpty()) {
            val current = queue.poll()
            val neighbours = generators.map { current * it }.toHashSet()
            graph[current] = neighbours
            queue.addAll(neighbours)
        }
        return graph
    }

    fun draw() {
        System.setProperty("org.graphstream.ui", "swing");
        val graph = SingleGraph("Cayley Graph")
        graph.isStrict = false
        graph.setAutoCreate(true)

        this.graph.keys.forEach { graph.addNode(it.toString()) }
        this.graph.forEach { (k, v) ->
            v.forEach { graph.addEdge("$k-$it", k.toString(), it.toString()) }
        }

        graph.setAttribute("ui.stylesheet", "node { fill-color: #aaffff; size: 10px; }")
        val viewer: Viewer = graph.display()
        viewer.enableAutoLayout()
        viewer.enableXYZfeedback(true)
    }
}