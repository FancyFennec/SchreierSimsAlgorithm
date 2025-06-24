import org.assertj.core.api.Assertions.assertThat
import org.example.CayleyGraph
import org.example.Permutation
import org.example.SchreierSims
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CayleyGraphTest {

    @Test
    fun cayleyGraphD4Test() {
        val graph = CayleyGraph(
            listOf(
                Permutation("(1,2,3,4)"),
                Permutation("(1,2)(3,4)")
            )
        )
        assertThat(graph.graph.size).isEqualTo(8)
    }

    @Test
    fun cayleyGraphSimsS6Test() {
        val graph = CayleyGraph(
            listOf(
                Permutation("(1,2)"),
                Permutation("(2,3)"),
                Permutation("(4,5)"),
                Permutation("(5,6)"),
                Permutation("(1,6)")
            ))
        assertThat(graph.graph.size).isEqualTo(720)
    }
}