package raytracer

object Matrices {

    val IDENTITY = new(
            arrayOf(1.0, 0.0, 0.0, 0.0),
            arrayOf(0.0, 1.0, 0.0, 0.0),
            arrayOf(0.0, 0.0, 1.0, 0.0),
            arrayOf(0.0, 0.0, 0.0, 1.0)
    )

    fun new(size: Int): Array<Array<Double>> {
        return Array(size) { Array(size) { 0.0 } }
    }

    fun new(vararg rows: Array<Double>): Array<Array<Double>> {
        return Array(rows.size) {
            i -> rows[i]
        }
    }

    fun isIdentical(matrixA: Array<Array<Double>>, matrixB: Array<Array<Double>>): Boolean {
        return matrixA.contentDeepEquals(matrixB)
    }

    fun multiply(matrixA: Array<Array<Double>>, matrixB: Array<Array<Double>>): Array<Array<Double>> {
        val product = new(matrixA.size);

        for (row in matrixA.indices) {
            for (col in matrixA.indices) {
                product[row][col] = matrixA[row][0] * matrixB[0][col] +
                        matrixA[row][1] * matrixB[1][col] +
                        matrixA[row][2] * matrixB[2][col] +
                        matrixA[row][3] * matrixB[3][col]
            }
        }

        return product
    }

    fun multiply(matrix: Array<Array<Double>>, tuple: Tuple): Tuple {
        val array = arrayOfNulls<Double>(4)

        for (row in matrix.indices) {
            array[row] = matrix[row][0] * tuple.x +
                    matrix[row][1] * tuple.y +
                    matrix[row][2] * tuple.z +
                    matrix[row][3] * tuple.w
        }

        return Tuple.point(array[0]!!, array[1]!!, array[2]!!)
    }

    fun transpose(matrix: Array<Array<Double>>): Array<Array<Double>> {
        val newMatrix = new(matrix.size);

        for (row in matrix.indices) {
            for (col in matrix.indices) {
                newMatrix[col][row] = matrix[row][col]
            }
        }

        return newMatrix
    }

    fun determinant(matrix: Array<Array<Double>>): Double {
        if (matrix.size == 2) {
            return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]
        }

        var determinant = 0.0
        for (column in matrix.indices) {
            determinant += matrix[0][column] * cofactor(matrix, 0, column)
        }
        return determinant
    }

    fun submatrix(matrix: Array<Array<Double>>, excludeRow: Int, excludeColumn: Int): Array<Array<Double>> {
        val submatrix = new(matrix.size - 1)

        var newRow = 0
        for (x in matrix.indices) {
            var newColumn = 0
            if (x != excludeRow) {
                for (y in matrix.indices) {
                    if (y != excludeColumn) {
                        submatrix[newRow][newColumn++] = matrix[x][y]
                    }
                }
                newRow++
            }
        }

        return submatrix
    }

    fun minor(matrix: Array<Array<Double>>, row: Int, column: Int): Double {
        return determinant(submatrix(matrix, row, column))
    }

    fun cofactor(matrix: Array<Array<Double>>, row: Int, column: Int): Double {
        val minor = minor(matrix, row, column)
        return if ((row + column) % 2 == 1) -minor else minor
    }

    fun isInvertible(matrix: Array<Array<Double>>): Boolean {
        return determinant(matrix) != 0.0
    }

    fun inverse(matrix: Array<Array<Double>>): Array<Array<Double>> {
        if (!isInvertible(matrix)) {
            throw RuntimeException("Not invertible")

        }
        val inverse = new(matrix.size)
        for (row in matrix.indices) {
            for (col in matrix.indices) {
                val cofactor = cofactor(matrix, row, col)
                inverse[col][row] = cofactor / determinant(matrix)
            }
        }
        return inverse
    }

}