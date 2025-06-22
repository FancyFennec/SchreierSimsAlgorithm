package org.example

import org.example.Permutation.Companion.E
import java.math.BigInteger
import java.util.*

class SchreierSims(
    private val generators: List<Permutation>,
    private val points: List<Int>
)
{

    constructor (generators: List<Permutation>) :
            this(generators, generators.flatMap { it.keys }.distinct())


    private val stabilizerPoint: Int = points.first()
    private val orbitMap: Map<Int, Permutation>
    public val stabilizerGenerators: List<Permutation>
    public val stabilizer : SchreierSims?

    init {
        this.orbitMap = computeOrbitMap()
        this.stabilizerGenerators = computeStabilizerGenerators()
        this.stabilizer = computeStabilizer()
    }

    val size : BigInteger = BigInteger.valueOf(this.orbitMap.size.toLong()).times((this.stabilizer?.size ?: BigInteger.ONE))

    private fun computeOrbitMap() : Map<Int, Permutation> {
        val result = mutableMapOf(stabilizerPoint to E)
        val queue: Queue<Int> = LinkedList(listOf(stabilizerPoint))
        while(queue.isNotEmpty()) {
            val current = queue.poll()!!
            generators.flatMap { g -> listOf(g, g.inv)}.forEach{ g ->
                val key = g * current
                if(key !in result) {
                    result[key] = g * result.getOrElse(current) {E}
                    queue.add(key)
                }
            }
        }
        return result
    }

    private fun computeStabilizer() : SchreierSims? {
        if(stabilizerGenerators.isEmpty() || points.size == 1) {
            return null
        }
        return SchreierSims(stabilizerGenerators, points.minus(stabilizerPoint))
    }

    private fun computeStabilizerGenerators() : List<Permutation> {
        return generators.flatMap { a ->
            orbitMap.values.map { u ->
                getComplement(a * u).inv * a * u
            }
        }.let(this::simsFilter)
    }

    private fun simsFilter(permutations: List<Permutation>) : List<Permutation> {
        val queue: Queue<Permutation> = LinkedList()
        queue.addAll(permutations)
        val result = mutableMapOf<String, Permutation>()
        while(queue.isNotEmpty()) {
            val current = queue.poll()!!
            if(current.isIdentity) {
                continue
            }
            val pair = getPair(current)
            if(pair !in result) {
                result[pair] = current
            } else {
                queue.add(current.inv * result[pair]!!)
            }
        }
        return result.values.distinct()
    }

    private fun getPair(p : Permutation) : String {
        val keys = p.keys
        for (i in keys.min() until keys.max()) {
            for (j in (i + 1)..keys.max()) {
               if(p * i == j) return "$i,$j"
            }
        }
        return ""
    }

    private fun getComplement(p : Permutation): Permutation {
        return orbitMap.getOrElse(p * stabilizerPoint) {E}
    }
}

