package raytracer

import kotlin.math.pow
import kotlin.math.sqrt

class Sphere {

    var transform = Matrices.IDENTITY

    fun intersect(ray: Ray): Intersections {
        val transformedRay = ray.transform(Matrices.inverse(transform))
        val sphereToRay = transformedRay.origin - Tuple.point(0.0, 0.0, 0.0)

        val a = transformedRay.direction.dot(transformedRay.direction)
        val b = 2 * transformedRay.direction.dot(sphereToRay)
        val c = sphereToRay.dot(sphereToRay) - 1

        val discriminant = b.pow(2) - 4 * a * c

        if (discriminant < 0) {
            return Intersections()
        }

        return Intersections(
                Intersection((-b - sqrt(discriminant)) / (2.0 * a), this),
                Intersection((-b + sqrt(discriminant)) / (2.0 * a), this)
        )
    }

}