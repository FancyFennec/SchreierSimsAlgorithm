import org.assertj.core.api.Assertions.assertThat
import org.example.Permutation
import org.example.SchreierSimsAlgorithm
import org.junit.jupiter.api.Test

class SchreierSimsTest {

    @Test
    fun schreierSimsGroupSizeTest() {
        val schreierSimsAlgorithm = SchreierSimsAlgorithm(listOf(Permutation("(1,9)(2,10,8)")))
        assertThat(schreierSimsAlgorithm.size).isEqualTo(6)
    }

    @Test
    fun schreierSimsStabilizerTest() {
        val schreierSimsAlgorithm = SchreierSimsAlgorithm(
            listOf(Permutation("(1,9)(2,10,8)")),
            listOf(1,9)
        )
        val stabilizer = schreierSimsAlgorithm.child!!
        assertThat(stabilizer.stabilizerGenerators).hasSize(1)
        assertThat(stabilizer.stabilizerGenerators[0].toString()).isEqualTo("(2,8,10)")
        assertThat(SchreierSimsAlgorithm(stabilizer.stabilizerGenerators).size).isEqualTo(3)
    }

    @Test
    fun schreierSimsD4Test() {
        val schreierSimsAlgorithm = SchreierSimsAlgorithm(
            listOf(
                Permutation("(1,2,3,4)"),
                Permutation("(1,2)(3,4)")
        ))
        assertThat(schreierSimsAlgorithm.size).isEqualTo(8)
    }

    @Test
    fun schreierSimsS5Test() {
        val schreierSimsAlgorithm = SchreierSimsAlgorithm(
            listOf(
                Permutation("(1,2)"),
                Permutation("(2,3)"),
                Permutation("(4,5)"),
                Permutation("(1,5)")
            ))
        assertThat(schreierSimsAlgorithm.size).isEqualTo(120)
    }

    @Test
    fun schreierSimsS6Test() {
        val schreierSimsAlgorithm = SchreierSimsAlgorithm(
            listOf(
                Permutation("(1,2)"),
                Permutation("(2,3)"),
                Permutation("(4,5)"),
                Permutation("(5,6)"),
                Permutation("(1,6)")
            ))
        assertThat(schreierSimsAlgorithm.size).isEqualTo(720)
    }

}