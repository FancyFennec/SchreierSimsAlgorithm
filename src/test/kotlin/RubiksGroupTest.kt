import org.example.RubiksCube

import org.assertj.core.api.Assertions.assertThat
import org.example.Permutation
import org.example.RubiksCube.Companion.L
import org.example.RubiksCube.Companion.R
import org.example.RubiksCube.Companion.U
import org.example.PocketCube
import org.junit.jupiter.api.Test
import java.math.BigInteger

class RubiksGroupTest {

    val rubiksCube = RubiksCube()
    val pocketCube = PocketCube()

    @Test
    fun solveSimpleCubeTest() {
        val solution = rubiksCube.solve(R * U * L*(R * U).inv * L)
        assertThat(solution).isEqualTo("R,U,L,-U,-R,L")
    }

    @Test
    fun solveSimpleCubeCommutatorTest() {
        val p = Permutation("(3,8,16,30,25,35)(5,13,10)(9,27,46,48,17,11)(26,47,28)")
        val solution = rubiksCube.solve(p)
        assertThat(solution).isEqualTo("-U,-R,U,R")
    }

    @Test
    fun solveCubeTest() {
        val p = R * U * L*(R * U).inv * L
        val solution = rubiksCube.solve(p)
        assertThat(rubiksCube.permute(solution)).isEqualTo(p)
    }

    @Test
    fun solveCubeWithInverseTest() {
        val p = R * U.inv
        val solution = rubiksCube.solve(p)
        assertThat(rubiksCube.permute(solution)).isEqualTo(p)
    }

    @Test
    fun threeCubeSizeTest() {
        assertThat(rubiksCube.size).isEqualTo(BigInteger("43252003274489856000"))
    }

    @Test
    fun twoCubeSizeTest() {
        assertThat(pocketCube.size).isEqualTo(88179840L)
    }

}