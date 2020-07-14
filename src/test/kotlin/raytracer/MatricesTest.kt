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

    @Test
    fun `Testing an invertible matrix for invertibility`() {
        val matrix = Matrices.new(
                arrayOf(6.0, 4.0, 4.0, 4.0),
                arrayOf(5.0, 5.0, 7.0, 6.0),
                arrayOf(4.0, -9.0, 3.0, -7.0),
                arrayOf(9.0, 1.0, 7.0, -6.0)
        )

        Matrices.determinant(matrix) shouldBe -2120.0
        Matrices.isInvertible(matrix) shouldBe true
    }

    @Test
    fun `Testing a noninvertible matrix for invertibility`() {
        val matrix = Matrices.new(
                arrayOf(-4.0, 2.0, -2.0, -3.0),
                arrayOf(9.0, 6.0, 2.0, 6.0),
                arrayOf(0.0, -5.0, 1.0, -5.0),
                arrayOf(0.0, 0.0, 0.0, 0.0)
        )

        Matrices.determinant(matrix) shouldBe 0.0
        Matrices.isInvertible(matrix) shouldBe false
    }

    @Test
    fun `Calculating the inverse of a matrix`() {
        val matrixA = Matrices.new(
                arrayOf(-5.0, 2.0, 6.0, -8.0),
                arrayOf(1.0, -5.0, 1.0, 8.0),
                arrayOf(7.0, 7.0, -6.0, -7.0),
                arrayOf(1.0, -3.0, 7.0, 4.0)
        )

        val matrixB = Matrices.inverse(matrixA)

        Matrices.determinant(matrixA) shouldBe 532.0
        Matrices.cofactor(matrixA, 2, 3) shouldBe -160.0
        matrixB[3][2] shouldBe -160.0 / 532.0
        Matrices.cofactor(matrixA, 3, 2) shouldBe 105.0
        matrixB[2][3] shouldBe 105.0 / 532.0

        Matrices.isIdentical(matrixB, Matrices.new(
                arrayOf(0.21805, 0.45113, 0.24060, -0.04511),
                arrayOf(-0.80827, -1.45677, -0.44361, 0.52068),
                arrayOf(-0.07895, -0.22368, -0.05263, 0.19737),
                arrayOf(-0.52256, -0.81391, -0.30075, 0.30639)
        ))
    }

    @Test
    fun `Calculating the inverse of another matrix`() {
        val matrixA = Matrices.new(
                arrayOf(8.0, -5.0, 9.0, 2.0),
                arrayOf(7.0, 5.0, 6.0, 1.0),
                arrayOf(-6.0, 0.0, 9.0, 6.0),
                arrayOf(-3.0, 0.0, -9.0, -4.0)
        )

        val matrixB = Matrices.inverse(matrixA)

        Matrices.isIdentical(matrixB, Matrices.new(
                arrayOf(-0.15385, -0.15385, -0.28205, -0.53846),
                arrayOf(-0.07692, 0.12308, 0.02564, 0.03077),
                arrayOf(0.35897, 0.35897, 0.43590, 0.92308),
                arrayOf(-0.69231, -0.69231, -0.76923, -1.92308)
        ))
    }

    @Test
    fun `Calculating the inverse of a third matrix`() {
        val matrixA = Matrices.new(
                arrayOf(9.0, 3.0, 0.0, 9.0),
                arrayOf(-5.0, -2.0, -6.0, -3.0),
                arrayOf(-4.0, 9.0, 6.0, 4.0),
                arrayOf(-7.0, 6.0, 6.0, 2.0)
        )

        val matrixB = Matrices.inverse(matrixA)

        Matrices.isIdentical(matrixB, Matrices.new(
                arrayOf(-0.04074, -0.07778, 0.14444, -0.22222),
                arrayOf(-0.07778, 0.03333, 0.36667, -0.33333),
                arrayOf(-0.02901, -0.14630, -0.10926, 0.12963),
                arrayOf(0.17778, 0.06667, -0.26667, 0.33333)
        ))
    }

    @Test
    fun `Multiplying a product by its inverse`() {
        val matrixA = Matrices.new(
                arrayOf(3.0, -9.0, 7.0, 3.0),
                arrayOf(3.0, -8.0, 2.0, -9.0),
                arrayOf(-4.0, 4.0, 4.0, 1.0),
                arrayOf(-6.0, 5.0, -1.0, 1.0)
        )

        val matrixB = Matrices.new(
                arrayOf(8.0, 2.0, 2.0, 2.0),
                arrayOf(3.0, -1.0, 7.0, 0.0),
                arrayOf(7.0, 0.0, 5.0, 4.0),
                arrayOf(6.0, -2.0, 0.0, 5.0)
        )

        val matrixC = Matrices.multiply(matrixA, matrixB)

        Matrices.isIdentical(Matrices.multiply(matrixC, Matrices.inverse(matrixB)), matrixA)
    }

}