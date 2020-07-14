package raytracer

import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

internal class MatricesTest {

    @Test
    fun `Creating a 4x4 matrix`() {
        val matrix = Matrices.new(
                arrayOf(1.0, 2.0, 3.0, 4.0),
                arrayOf(5.5, 6.5, 7.5, 8.5),
                arrayOf(9.0, 10.0, 11.0, 12.0),
                arrayOf(13.5, 14.5, 15.5, 16.5)
        )

        matrix[0][0] shouldBe 1.0
        matrix[0][3] shouldBe 4.0
        matrix[1][0] shouldBe 5.5
        matrix[1][2] shouldBe 7.5
        matrix[2][2] shouldBe 11.0
        matrix[3][0] shouldBe 13.5
        matrix[3][2] shouldBe 15.5
    }

    @Test
    fun `Matrix equality with identical matrices`() {
        val matrixA = Matrices.new(
                arrayOf(1.0, 2.0, 3.0, 4.0),
                arrayOf(5.5, 6.5, 7.5, 8.5),
                arrayOf(9.0, 10.0, 11.0, 12.0),
                arrayOf(13.5, 14.5, 15.5, 16.5)
        )
        val matrixB = Matrices.new(
                arrayOf(1.0, 2.0, 3.0, 4.0),
                arrayOf(5.5, 6.5, 7.5, 8.5),
                arrayOf(9.0, 10.0, 11.0, 12.0),
                arrayOf(13.5, 14.5, 15.5, 16.5)
        )

        Matrices.isIdentical(matrixA, matrixB) shouldBe true
    }

    @Test
    fun `Matrix equality with different matrices`() {
        val matrixA = Matrices.new(
                arrayOf(1.0, 2.0, 3.0, 4.0),
                arrayOf(5.5, 6.5, 7.5, 8.5),
                arrayOf(9.0, 10.0, 11.0, 12.0),
                arrayOf(13.5, 14.5, 15.5, 1.0)
        )
        val matrixB = Matrices.new(
                arrayOf(1.0, 2.0, 3.0, 4.0),
                arrayOf(5.5, 6.5, 7.5, 8.5),
                arrayOf(9.0, 10.0, 11.0, 12.0),
                arrayOf(13.5, 14.5, 15.5, 16.5)
        )

        Matrices.isIdentical(matrixA, matrixB) shouldBe false
    }

    @Test
    fun `Multiplying two matrices`() {
        val matrixA = Matrices.new(
                arrayOf(1.0, 2.0, 3.0, 4.0),
                arrayOf(5.0, 6.0, 7.0, 8.0),
                arrayOf(9.0, 8.0, 7.0, 6.0),
                arrayOf(5.0, 4.0, 3.0, 2.0)
        )
        val matrixB = Matrices.new(
                arrayOf(-2.0, 1.0, 2.0, 3.0),
                arrayOf(3.0, 2.0, 1.0, -1.0),
                arrayOf(4.0, 3.0, 6.0, 5.0),
                arrayOf(1.0, 2.0, 7.0, 8.0)
        )

        Matrices.isIdentical(
                Matrices.multiply(matrixA, matrixB),
                Matrices.new(
                        arrayOf(20.0, 22.0, 50.0, 48.0),
                        arrayOf(44.0, 54.0, 114.0, 108.0),
                        arrayOf(40.0, 58.0, 110.0, 102.0),
                        arrayOf(16.0, 26.0, 46.0, 42.0)
                )
        )
    }

    @Test
    fun `A matrix multiplied by a tuple`() {
        val matrix = Matrices.new(
                arrayOf(1.0, 2.0, 3.0, 4.0),
                arrayOf(2.0, 4.0, 4.0, 2.0),
                arrayOf(8.0, 6.0, 4.0, 1.0),
                arrayOf(0.0, 0.0, 0.0, 1.0)
        )
        val tuple = Tuple.point(1.0, 2.0, 3.0)

        Matrices.multiply(matrix, tuple) shouldBe Tuple.point(18.0, 24.0, 33.0)
    }

    @Test
    fun `Multiplying a matrix by the identity matrix`() {
        val matrix = Matrices.new(
                arrayOf(0.0, 1.0, 2.0, 4.0),
                arrayOf(1.0, 2.0, 4.0, 8.0),
                arrayOf(2.0, 4.0, 8.0, 16.0),
                arrayOf(4.0, 8.0, 16.0, 32.0)
        )

        Matrices.isIdentical(Matrices.multiply(matrix, Matrices.IDENTITY), matrix)
    }

    @Test
    fun `Multiplying the identity matrix by a tuple`() {
        val tuple = Tuple.point(1.0, 2.0, 3.0)

        Matrices.multiply(Matrices.IDENTITY, tuple) shouldBe tuple
    }

    @Test
    fun `Transposing a matrix`() {
        val matrix = Matrices.new(
                arrayOf(0.0, 9.0, 3.0, 0.0),
                arrayOf(9.0, 8.0, 0.0, 8.0),
                arrayOf(1.0, 8.0, 5.0, 3.0),
                arrayOf(0.0, 0.0, 5.0, 8.0)
        )

        Matrices.isIdentical(Matrices.transpose(matrix), Matrices.new(
                arrayOf(0.0, 9.0, 1.0, 0.0),
                arrayOf(9.0, 8.0, 8.0, 0.0),
                arrayOf(3.0, 0.0, 5.0, 5.0),
                arrayOf(0.0, 8.0, 3.0, 8.0)
        ))
    }

    @Test
    fun `Transposing the identity matrix`() {
        Matrices.isIdentical(Matrices.transpose(Matrices.IDENTITY), Matrices.IDENTITY)
    }

    @Test
    fun `Calculating the determinant of a 2x2 matrix`() {
        val matrix = Matrices.new(
                arrayOf(1.0, 5.0),
                arrayOf(-3.0, 2.0)
        )

        Matrices.determinant(matrix) shouldBe 17.0
    }

    @Test
    fun `A submatrix of a 3x3 matrix is a 2x2 matrix`() {
        val matrix = Matrices.new(
                arrayOf(1.0, 5.0, 0.0),
                arrayOf(-3.0, 2.0, 7.0),
                arrayOf(0.0, 6.0, -3.0)
        )

        Matrices.isIdentical(Matrices.submatrix(matrix, 0, 2), Matrices.new(
                arrayOf(-3.0, 2.0),
                arrayOf(0.0, 6.0)
        ))
    }

    @Test
    fun `A submatrix of a 4x4 matrix is a 3x3 matrix`() {
        val matrix = Matrices.new(
                arrayOf(-6.0, 1.0, 1.0, 6.0),
                arrayOf(-8.0, 5.0, 8.0, 6.0),
                arrayOf(-1.0, 0.0, 8.0, 2.0),
                arrayOf(-7.0, 1.0, -1.0, 1.0)
        )

        Matrices.isIdentical(Matrices.submatrix(matrix, 2, 1), Matrices.new(
                arrayOf(-6.0, 1.0, 6.0),
                arrayOf(-8.0, 8.0, 6.0),
                arrayOf(-7.0, -1.0, 1.0)
        ))
    }

    @Test
    fun `Calculating a minor of a 3x3 matrix`() {
        val matrixA = Matrices.new(
                arrayOf(3.0, 5.0, 0.0),
                arrayOf(2.0, -1.0, -7.0),
                arrayOf(6.0, -1.0, 5.0)
        )

        val matrixB = Matrices.submatrix(matrixA, 1, 0)

        Matrices.determinant(matrixB) shouldBe 25.0
        Matrices.minor(matrixA, 1, 0) shouldBe 25.0
    }

    @Test
    fun `Calculating a cofactor of a 3x3 matrix`() {
        val matrix = Matrices.new(
                arrayOf(3.0, 5.0, 0.0),
                arrayOf(2.0, -1.0, -7.0),
                arrayOf(6.0, -1.0, 5.0)
        )

        Matrices.minor(matrix, 0, 0) shouldBe -12.0
        Matrices.cofactor(matrix, 0, 0) shouldBe -12.0
        Matrices.minor(matrix, 1, 0) shouldBe 25.0
        Matrices.cofactor(matrix, 1, 0) shouldBe -25.0

    }

    @Test
    fun `Calculating the determinant of a 3x3 matrix`() {
        val matrix = Matrices.new(
                arrayOf(1.0, 2.0, 6.0),
                arrayOf(-5.0, 8.0, -4.0),
                arrayOf(2.0, 6.0, 4.0)
        )

        Matrices.cofactor(matrix, 0, 0) shouldBe 56.0
        Matrices.cofactor(matrix, 0, 1) shouldBe 12.0
        Matrices.cofactor(matrix, 0, 2) shouldBe -46.0
        Matrices.determinant(matrix) shouldBe -196.0
    }

}