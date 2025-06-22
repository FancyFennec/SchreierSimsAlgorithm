import org.assertj.core.api.Assertions.assertThat
import org.example.Permutation
import org.example.SchreierSims
import org.junit.jupiter.api.Test

class SchreierSimsTest {

    @Test
    fun schreierSimsGroupSizeTest() {
        val schreierSims = SchreierSims(listOf(Permutation("(1,9)(2,10,8)")))
        assertThat(schreierSims.size).isEqualTo(6)
    }

    @Test
    fun schreierSimsStabilizerTest() {
        val schreierSims = SchreierSims(
            listOf(Permutation("(1,9)(2,10,8)")),
            listOf(1,9)
        )
        val stabilizer = schreierSims.stabilizer!!
        assertThat(stabilizer.stabilizerGenerators).hasSize(1)
        assertThat(stabilizer.stabilizerGenerators[0].toString()).isEqualTo("(2,8,10)")
        assertThat(SchreierSims(stabilizer.stabilizerGenerators).size).isEqualTo(3)
    }

    @Test
    fun schreierSimsD4Test() {
        val schreierSims = SchreierSims(
            listOf(
                Permutation("(1,2,3,4)"),
                Permutation("(1,2)(3,4)")
        ))
        assertThat(schreierSims.size).isEqualTo(8)
    }

    @Test
    fun schreierSimsS5Test() {
        val schreierSims = SchreierSims(
            listOf(
                Permutation("(1,2)"),
                Permutation("(2,3)"),
                Permutation("(4,5)"),
                Permutation("(1,5)")
            ))
        assertThat(schreierSims.size).isEqualTo(120)
    }

    @Test
    fun schreierSimsS6Test() {
        val schreierSims = SchreierSims(
            listOf(
                Permutation("(1,2)"),
                Permutation("(2,3)"),
                Permutation("(4,5)"),
                Permutation("(5,6)"),
                Permutation("(1,6)")
            ))
        assertThat(schreierSims.size).isEqualTo(720)
    }

}