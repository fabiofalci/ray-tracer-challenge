package raytracer

import kotlin.math.pow
import kotlin.math.sqrt

class Tuple(
        val x: Double,
        val y: Double,
        val z: Double,
        val w: Int
) {

    companion object {
        fun vector(x: Double, y: Double, z: Double) : Tuple = Tuple(x, y, z, 0)
        fun point(x: Double, y: Double, z: Double) : Tuple = Tuple(x, y, z, 1)
        fun color(red: Double, green: Double, blue: Double) : Tuple = Tuple(red, green, blue, 2)
    }

    override fun equals(other: Any?): Boolean = (other is Tuple) && x == other.x && y == other.y && z == other.z && w == other.w

    override fun toString(): String {
        val type = if (isPoint()) "Point" else if (isVector()) "Vector" else if (isColor()) "Color" else "Tuple"
        return "$type $x, $y, $z, $w"
    }

    fun isPoint(): Boolean {
        return w == 1
    }

    fun isVector(): Boolean {
        return w == 0
    }

    fun isColor(): Boolean {
        return w == 2
    }

    operator fun plus(tuple: Tuple): Tuple {
        if (isPoint() && tuple.isPoint()) throw RuntimeException("Cannot add two points")
        return Tuple(
                x + tuple.x,
                y + tuple.y,
                z + tuple.z,
                w + if (isColor()) 0 else tuple.w
        )
    }

    operator fun minus(tuple: Tuple): Tuple {
        if (isVector() && tuple.isPoint()) throw RuntimeException("Cannot subtract a point from a vector")
        return Tuple(
                x - tuple.x,
                y - tuple.y,
                z - tuple.z,
                w - if (isColor()) 0 else tuple.w
        )
    }

    operator fun unaryMinus(): Tuple {
        return Tuple(-x, -y, -z, -w)
    }

    operator fun times(d: Double): Tuple {
        return Tuple(x * d, y * d, z * d, (w * d).toInt())
    }

    operator fun times(tuple: Tuple): Any {
        if (!isColor()) throw RuntimeException("Cannot multiply non color tuples")
        return Tuple(x * tuple.x, y * tuple.y, z * tuple.z, w)
    }

    operator fun times(i: Int): Tuple {
        if (!isColor()) throw RuntimeException("Cannot multiply non color tuples")
        return Tuple(x * i, y * i, z * i, w)
    }

    operator fun div(i: Int): Any {
        return Tuple(x / i, y / i, z / i, w / i)
    }

    fun magnitude(): Double {
        return sqrt(x.pow(2.0) + y.pow(2.0) + z.pow(2.0) + w.toDouble().pow(2.0))
    }

    fun normalize(): Tuple {
        val magnitude = magnitude()
        return Tuple(x / magnitude, y / magnitude, z / magnitude, (w / magnitude).toInt())
    }

    fun dot(tuple: Tuple): Double {
        return x * tuple.x + y * tuple.y + z * tuple.z + w + tuple.w
    }

    fun cross(tuple: Tuple): Tuple {
        return vector(
                y * tuple.z - z * tuple.y,
                z * tuple.x - x * tuple.z,
                x * tuple.y - y * tuple.x
        )
    }
}