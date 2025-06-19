import org.example.ThreeRubiksCube

import org.assertj.core.api.Assertions.assertThat
import org.example.Permutation
import org.example.ThreeRubiksCube.Companion.L
import org.example.ThreeRubiksCube.Companion.R
import org.example.ThreeRubiksCube.Companion.U
import org.junit.jupiter.api.Test

class RubiksCubeTest {

    val rubiksCube = ThreeRubiksCube()

    @Test
    fun solveSimpleCubeTest() {
        val solution = rubiksCube.solve(R * U * L*(R * U).inv * L)
        assertThat(solution).isEqualTo("R,U,L,-U,-R,L")
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
    fun solveComplexCubeTest() {
        val pin = Permutation("(9,17,46)(22,38,41)(32,43,40)")
        val solution = rubiksCube.solve(pin)
        val pout = rubiksCube.permute("F,-D,U,-F,D,R,-D,-R,F,-L")
        assertThat(pin).isEqualTo(pout)
    }



}