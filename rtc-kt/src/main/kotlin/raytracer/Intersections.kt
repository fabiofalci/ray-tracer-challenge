package raytracer

class Intersections(vararg intersection: Intersection) {
    val list: List<Intersection> = intersection.toCollection(ArrayList()).sortedBy { it.time }
    val size: Int = list.size

    override fun toString(): String {
        return "Intersections size $size"
    }

    fun hit(): Intersection? {
        return list.find { it.time > 0 }
    }
}