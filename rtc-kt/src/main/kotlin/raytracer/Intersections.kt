package raytracer

class Intersections(vararg intersection: Intersection) {
    val list: List<Intersection> = intersection.toCollection(ArrayList()).sortedBy { it.time }
    val size: Int = list.size

    fun hit(): Intersection? {
        return list.find { it.time > 0 }
    }
}