package raytracer

import kotlin.math.cos
import kotlin.math.sin

object Transformations {

    fun translation(x: Double, y: Double, z: Double): Array<Array<Double>> { return Matrices.new(
                arrayOf(1.0, 0.0, 0.0, x),
                arrayOf(0.0, 1.0, 0.0, y),
                arrayOf(0.0, 0.0, 1.0, z),
                arrayOf(0.0, 0.0, 0.0, 1.0)
        )
    }

    fun scaling(x: Double, y: Double, z: Double): Array<Array<Double>> {
        return Matrices.new(
                arrayOf(x, 0.0, 0.0, 0.0),
                arrayOf(0.0, y, 0.0, 0.0),
                arrayOf(0.0, 0.0, z, 0.0),
                arrayOf(0.0, 0.0, 0.0, 1.0)
        )
    }

    fun rotationX(radians: Double): Array<Array<Double>> {
        return Matrices.new(
                arrayOf(1.0, 0.0, 0.0, 0.0),
                arrayOf(0.0, cos(radians), -sin(radians), 0.0),
                arrayOf(0.0, sin(radians), cos(radians), 0.0),
                arrayOf(0.0, 0.0, 0.0, 1.0)
        )
    }

    fun rotationY(radians: Double): Array<Array<Double>> {
        return Matrices.new(
                arrayOf(cos(radians), 0.0, sin(radians), 0.0),
                arrayOf(0.0, 1.0, 0.0, 0.0),
                arrayOf(-sin(radians), 0.0, cos(radians), 0.0),
                arrayOf(0.0, 0.0, 0.0, 1.0)
        )
    }

    fun rotationZ(radians: Double): Array<Array<Double>> {
        return Matrices.new(
                arrayOf(cos(radians), -sin(radians), 0.0, 0.0),
                arrayOf(sin(radians), cos(radians), 0.0, 0.0),
                arrayOf(0.0, 0.0, 1.0, 0.0),
                arrayOf(0.0, 0.0, 0.0, 1.0)
        )
    }

}