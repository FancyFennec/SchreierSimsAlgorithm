package org.example

fun main(args: Array<String>) {
    assert(args.size == 3)
    if(args.size < 3) {
        throw IllegalArgumentException("Three arguments expected, but was: ${args.size}")
    }

    println("Using Generators: ${args[0]}")
    val rubiksCube = RubiksCube.Builder()
        .let { if(args[0].contains("F")) it.withF() else it }
        .let { if(args[1].contains("B")) it.withB() else it }
        .let { if(args[0].contains("U")) it.withU() else it }
        .let { if(args[0].contains("D")) it.withD() else it }
        .let { if(args[0].contains("L")) it.withL() else it }
        .let { if(args[0].contains("R")) it.withR() else it }
        .build()
    println("Computing group size...")
    println("Group size: ${SchreierSims(rubiksCube.generators).size}")

    println("Computing subgroup that permute: ${args[1]}")
    val stabilizers = args[1].split(",")
        .map { it.toInt() }
        .let(rubiksCube::stabilizersThatPermute)
    println("Computing subgroup size...")
    println("Subgroup size: ${SchreierSims(stabilizers).size}")

    if(args[2].isBlank()) {
        println("Computing all solutions for permutations in subgroup.")
    } else {
        val map = args[2].split(";")
            .joinToString(", ") { p -> p }
        println("Computing solutions that contain: $map")
    }
    val cayleyGraph = CayleyGraph(stabilizers)
    val sortedPermutations = cayleyGraph.graph.keys.let { permutations ->
        if(args[2].isBlank()) {
            permutations
        } else {
            permutations.filter {
                args[2].split(";")
                    .any{ f -> it.toString().contains(f) }
            }
        }
    }
    val sortedSolutions = sortedPermutations
        .map { rubiksCube.solve(it) }
        .filter { it.isNotEmpty() }
        .sortedBy { it.split(",").size }
    println("Found ${sortedSolutions.size} solutions:")
    sortedSolutions
        .forEach { g ->
            println("Solution ${sortedSolutions.indexOf(g) + 1}: ${rubiksCube.permute(g)}")
            println("Path: $g")
        }
}

