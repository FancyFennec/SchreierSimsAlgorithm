package org.example

import org.graphstream.graph.implementations.SingleGraph
import org.graphstream.ui.view.Viewer

fun main() {
    val U = Permutation("(1,2,4,3)(23,14,6,10)(24,13,5,9)")
    val F = Permutation("(5,6,8,7)(3,13,18,12)(4,15,17,10)")
    val R = Permutation("(13,14,16,15)(4,24,20,8)(2,22,18,6)")
    val L = Permutation("(9,10,12,11)(1,5,17,21)(3,7,19,23)")

//    val F = Permutation("(1,7,9,3)(2,4,8,6)(12,37,34,27)(15,38,31,26)(18,39,28,25)")
//    val L = Permutation("(1,19,46,37)(4,22,49,40)(7,25,52,43)(10,16,18,12)(11,13,17,15)")
//    val T = Permutation("(1,28,54,10)(2,29,53,11)(3,30,52,12)(19,25,27,21)(20,22,26,24)")
//    val R = Permutation("(3,39,48,21)(6,42,51,24)(9,45,54,27)(28,34,36,30)(29,31,35,33)")
//    val U = Permutation("(7,16,48,34)(8,17,47,35)(9,18,46,36)(37,43,45,39)(38,40,44,42)")
//    val B = Permutation("(10,21,36,43)(13,20,33,44)(16,19,30,45)(46,52,54,48)(47,49,53,51)")


    val a = Permutation("(1,5,7)(2,6,8)")
    val b = Permutation("(1,5)(3,4,8,2)")

    val c = Permutation("(2,6,4,3)(5,7)")
    val d = Permutation("(2,3,4,6)(5,7)")
    val e = Permutation("(2,4)(3,8)")
    val f = Permutation("(2,6,3,4)(5,7)")

    val generators = mutableListOf(
//        U, R
//        U, F, R
//        U * R / U / R,
        a, b
//        c, d, e, f
//        U * R * R / L
    )

    var g = Group(mutableListOf(U, R, F))
//    val cayleyGraph = generateCayleyGraph(generators.flatMap { g -> mutableListOf(g, g) })

    println(g.size)
//    println(cayleyGraph.size)

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