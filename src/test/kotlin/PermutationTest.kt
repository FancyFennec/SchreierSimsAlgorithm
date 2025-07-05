import org.assertj.core.api.Assertions.assertThat
import org.example.Permutation
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class PermutationTest {

    @Test
    fun serializePermutationTest() {
        assertThat(Permutation("(10,8,2)(9,1)").toString())
            .isEqualTo("(1,9)(2,10,8)")
    }

    @Test
    fun inversePermutationTest() {
        assertThat(Permutation("(1,9)(2,10,8)").inv.toString())
            .isEqualTo("(1,9)(2,8,10)")
    }

    @Test
    fun multiplyPermutationTest() {
        val p1 = Permutation("(1,9)(2,10,8)")
        val p2 = Permutation("(1,8)(4,6,2)")
        assertThat((p2 * p1).toString()).isEqualTo("(1,9,8,4,6,2,10)")
    }

    @Test
    fun applyPermutationTest() {
        val p = Permutation("(1,9)(2,10,8)")
        assertAll(
            { assertThat(p * 1).isEqualTo(9) },
            { assertThat(p * 2).isEqualTo(10) },
            { assertThat(p * 8).isEqualTo(2) }
        )
    }

    @Test
    fun equalPermutationTest() {
        val p1 = Permutation("(1,9)(2,10,8)")
        val p2 = Permutation("(1,9)(10,8,2)")
        assertThat(p1 == p2).isTrue()
    }
}