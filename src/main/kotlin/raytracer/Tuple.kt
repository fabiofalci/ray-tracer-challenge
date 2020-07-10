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

    fun isPoint(): Boolean {
        return w == 1
    }

    fun isVector(): Boolean {
        return w == 0
    }
}