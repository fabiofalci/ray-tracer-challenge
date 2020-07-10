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
        fun point(x: Double, y: Double, z: Double) : Tuple = Tuple(x, y, z, 1)
        fun vector(x: Double, y: Double, z: Double) : Tuple = Tuple(x, y, z, 0)
    }

    override fun equals(other: Any?): Boolean = (other is Tuple) && x == other.x && y == other.y && z == other.z && w == other.w

    override fun toString(): String {
        val type = if (isPoint()) "Point" else if (isVector()) "Vector" else "Tuple"
        return "$type $x, $y, $z, $w"
    }

    fun isPoint(): Boolean {
        return w == 1
    }

    fun isVector(): Boolean {
        return w == 0
    }

    operator fun plus(tuple: Tuple): Tuple {
        if (isPoint() && tuple.isPoint()) throw RuntimeException("Cannot add two points")
        return Tuple(x + tuple.x, y + tuple.y, z + tuple.z, w + tuple.w)
    }

    operator fun minus(tuple: Tuple): Tuple {
        if (isVector() && tuple.isPoint()) throw RuntimeException("Cannot subtract a point from a vector")
        return Tuple(x - tuple.x, y - tuple.y, z - tuple.z, w - tuple.w)
    }

    operator fun unaryMinus(): Tuple {
        return Tuple(-x, -y, -z, -w)
    }

    operator fun times(d: Double): Tuple {
        return Tuple(x * d, y * d, z * d, (w * d).toInt())
    }

    operator fun div(i: Int): Any {
        return Tuple(x / i, y / i, z / i, w / i)
    }

    fun magnitude(): Double {
        return sqrt(x.pow(2.0) + y.pow(2.0) + z.pow(2.0) + w.toDouble().pow(2.0))
    }
}