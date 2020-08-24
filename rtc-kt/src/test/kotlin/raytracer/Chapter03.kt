package raytracer

import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

internal class Chapter03Test {

    @Test
    fun test() {
        val inversed = Matrices.inverse(Matrices.IDENTITY)

        Matrices.print(inversed)
    }

    @Test
    fun `Multiply by its inverse`() {
        val matrix = Matrices.new(
                arrayOf(6.0, 4.0, 4.0, 4.0),
                arrayOf(5.0, 5.0, 7.0, 6.0),
                arrayOf(4.0, -9.0, 3.0, -7.0),
                arrayOf(9.0, 1.0, 7.0, -6.0)
        )

        Matrices.print(Matrices.multiply(matrix, Matrices.inverse(matrix)))
    }

    @Test
    fun `Inverse of the transpose versus transpose of the inverse`() {
        val matrix = Matrices.new(
                arrayOf(6.0, 4.0, 4.0, 4.0),
                arrayOf(5.0, 5.0, 7.0, 6.0),
                arrayOf(4.0, -9.0, 3.0, -7.0),
                arrayOf(9.0, 1.0, 7.0, -6.0)
        )

        Matrices.print(Matrices.inverse(Matrices.transpose(matrix)))
        println()
        Matrices.print(Matrices.transpose(Matrices.inverse(matrix)))
    }

    @Test
    fun `Identity modified multiply tuple`() {
        val tuple = Tuple.point(1.0, 2.0, 3.0)

        val matrix = Matrices.new(
                arrayOf(1.0, 0.0, 0.0, 0.0),
                arrayOf(0.0, 1.0, 0.0, 0.0),
                arrayOf(0.0, 0.0, 2.0, 0.0),
                arrayOf(0.0, 0.0, 0.0, 1.0)
        )

        println(Matrices.multiply(Matrices.IDENTITY, tuple))
        println(Matrices.multiply(matrix, tuple))
    }

}