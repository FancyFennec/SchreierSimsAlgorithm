package org.example

import org.graphstream.graph.implementations.SingleGraph
import org.graphstream.ui.view.Viewer

fun main() {
    val rubiksCube = SimpleThreeRubiksCube()
//    val pin = Permutation("(9,17,46)(22,38,41)(32,43,40)")
//    val solution = rubiksCube.solve(pin)
//    val pout = rubiksCube.permute(solution)
    val stabilizers = rubiksCube.stabilizersThatPermute(
        mutableListOf(
//            9,10,11,12,13,14,15,16,
//            1,2,3,17,18,19,25,26,26,46,47,48,
//            5,28

//            3, 16, 25, 8, 30, 35

            9,10,11,12,13,14,15,16,
            1,2,3,17,18,19,25,26,27,46,47,48,
            37,31,
            8,30,25
        )
    )
    stabilizers.forEach { g ->
        println("Stabilizer $g")
        println("Path: ${rubiksCube.solve(g)}")
    }

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