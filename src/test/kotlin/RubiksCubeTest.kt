import org.example.ThreeRubiksCube

import org.assertj.core.api.Assertions.assertThat
import org.example.ThreeRubiksCube.Companion.L
import org.example.ThreeRubiksCube.Companion.R
import org.example.ThreeRubiksCube.Companion.U
import org.example.TwoRubiksCube
import org.junit.jupiter.api.Test

class RubiksCubeTest {

    val threeRubiksCube = ThreeRubiksCube()
    val twoRubiksCube = TwoRubiksCube()

    @Test
    fun solveSimpleCubeTest() {
        val solution = threeRubiksCube.solve(R * U * L*(R * U).inv * L)
        assertThat(solution).isEqualTo("R,U,L,-U,-R,L")
    }

    @Test
    fun solveCubeTest() {
        val p = R * U * L*(R * U).inv * L
        val solution = threeRubiksCube.solve(p)
        assertThat(threeRubiksCube.permute(solution)).isEqualTo(p)
    }

    @Test
    fun solveCubeWithInverseTest() {
        val p = R * U.inv
        val solution = threeRubiksCube.solve(p)
        assertThat(threeRubiksCube.permute(solution)).isEqualTo(p)
    }

    @Test
    fun threeCubeSizeTest() {
        assertThat(threeRubiksCube.size).isEqualTo(6358515127070752768L)
    }

    @Test
    fun twoCubeSizeTest() {
        assertThat(twoRubiksCube.size).isEqualTo(88179840L)
    }

}