package raytracer

class Intersection(val time: Double, val obj: Sphere) {


    companion object {
        fun intersections(vararg intersection: Intersection): List<Intersection> {
            return intersection.toCollection(ArrayList())
        }
    }
}