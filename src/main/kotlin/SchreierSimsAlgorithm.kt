package org.example

import org.example.Permutation.Companion.E
import java.math.BigInteger
import java.util.*

class SchreierSimsAlgorithm(private val generators: List<Permutation>, private val points: List<Int>) {

    constructor (generators: List<Permutation>) :
            this(generators, generators.flatMap { it.keys }.distinct())

    private val stabilizerPoint: Int = points.first()
    private val schreierVector: Map<Int, Permutation>
    val stabilizerGenerators: List<Permutation>
    val child: SchreierSimsAlgorithm?

    init {
        this.schreierVector = computeSchreierVector()
        this.stabilizerGenerators = computeStabilizerGenerators()
        this.child = computeChild()
    }

    val size: BigInteger = BigInteger.valueOf(this.schreierVector.size.toLong())
        .times((this.child?.size ?: BigInteger.ONE))

    private fun computeSchreierVector(): Map<Int, Permutation> {
        val result = mutableMapOf(stabilizerPoint to E)
        val queue: Queue<Int> = LinkedList(listOf(stabilizerPoint))
        while (queue.isNotEmpty()) {
            val current = queue.poll()!!
            generators.flatMap { g -> listOf(g, g.inv) }.forEach { g ->
                val key = g * current
                if (key !in result) {
                    result[key] = g * result.getOrElse(current) { E }
                    queue.add(key)
                }
            }
        }
        return result
    }

    private fun computeChild(): SchreierSimsAlgorithm? {
        if (stabilizerGenerators.isEmpty() || points.size == 1) {
            return null
        }
        return SchreierSimsAlgorithm(stabilizerGenerators, points.minus(stabilizerPoint))
    }

    private fun computeStabilizerGenerators(): List<Permutation> {
        return generators.flatMap { a ->
            schreierVector.values.map { u ->
                computeStabilizerGenerator(a, u)
            }
        }.let(this::applySimsFilter)
    }

    private fun computeStabilizerGenerator(a: Permutation, u: Permutation): Permutation {
        val key = a * u * stabilizerPoint
        return schreierVector.getOrElse(key) { E }.inv * a * u
    }

    private fun applySimsFilter(generators: List<Permutation>): List<Permutation> {
        val queue: Queue<Permutation> = LinkedList()
        queue.addAll(generators)
        val result = mutableMapOf<String, Permutation>()
        while (queue.isNotEmpty()) {
            val current = queue.poll()!!
            if (current.isIdentity) {
                continue
            }
            val key = getSimsKey(current)
            if (key !in result) {
                result[key] = current
            } else {
                queue.add(current.inv * result[key]!!)
            }
        }
        return result.values.distinct()
    }

    private fun getSimsKey(p: Permutation): String {
        if (p.keys.isEmpty()) return ""
        val i = p.keys.min()
        val j = p * i
        return "$i,$j"
    }

}

