package raytracer

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
        val type = if (isPoint()) "Point" else "Vector"
        return "$type $x $y $z"
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
        return Tuple(-x, -y, -z, w)
    }
}