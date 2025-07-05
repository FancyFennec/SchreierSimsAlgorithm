package org.example

private val generatorsRegex = Regex("""^(?:\w,)*\w$""")
private val facesToPermuteRegex = Regex("""^(?:\d+,)*\d+$""")
private val cycleToFilterRegex = Regex("""^(\((?:\d+,)*\d+\),)*\((?:\d+,)*\d+\)$""")

fun main(args: Array<String>) {
    require(args.size >= 2) { "At least two arguments expected, but was: ${args.size}." }
    require(generatorsRegex.matches(args[0])) { "Generators have invalid format: $args[0]" }
    require(facesToPermuteRegex.matches(args[1])) { "Faces to permute have invalid format: $args[1]" }

    println("Using Generators: ${args[0]}")
    val rubiksCube = RubiksCube.Builder()
        .let { if (args[0].contains("F")) it.withF() else it }
        .let { if (args[1].contains("B")) it.withB() else it }
        .let { if (args[0].contains("U")) it.withU() else it }
        .let { if (args[0].contains("D")) it.withD() else it }
        .let { if (args[0].contains("L")) it.withL() else it }
        .let { if (args[0].contains("R")) it.withR() else it }
        .build()
    println("Computing group size...")
    println("Group size: ${SchreierSimsAlgorithm(rubiksCube.generators).size}")

    println("Computing subgroup that permute: ${args[1]}")
    val stabilizers = args[1].split(",")
        .map { it.toInt() }
        .let(rubiksCube::stabilizersThatPermute)
    if (stabilizers.isEmpty()) {
        println("Unable to permute faces...")
        return
    }
    println("Computing subgroup size...")
    println("Subgroup size: ${SchreierSimsAlgorithm(stabilizers).size}")

    if (args.size < 3 || args[2].isBlank()) {
        println("Computing all solutions for permutations in subgroup.")
    } else {
        val map = args[2].split(";")
            .joinToString(", ") { p -> p }
        println("Computing solutions that contain cycle: $map")
    }
    val cayleyGraph = CayleyGraph(stabilizers)
    val sortedPermutations = cayleyGraph.graph.keys.let { permutations ->
        if (args.size < 3 || args[2].isBlank()) {
            permutations
        } else {
            require(cycleToFilterRegex.matches(args[2])) { "Cycle to filter has invalid format." }
            permutations.filter {
                args[2].split(";")
                    .any { f -> it.toString().contains(f) }
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

