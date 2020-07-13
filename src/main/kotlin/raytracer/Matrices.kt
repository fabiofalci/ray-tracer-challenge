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

}