package raytracer

class Intersections(vararg intersection: Intersection) {

    val list: List<Intersection> = intersection.toCollection(ArrayList())
    val size: Int = list.size

}