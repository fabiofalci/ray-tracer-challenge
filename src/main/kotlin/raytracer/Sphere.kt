package raytracer

import kotlin.math.pow
import kotlin.math.sqrt

class Sphere {

    fun intersect(ray: Ray): Array<Double> {
        val sphereToRay = ray.origin - Tuple.point(0.0, 0.0, 0.0)

        val a = ray.direction.dot(ray.direction)
        val b = 2 * ray.direction.dot(sphereToRay)
        val c = sphereToRay.dot(sphereToRay) - 1

        val discriminant = b.pow(2) - 4 * a * c

        if (discriminant < 0) {
            return emptyArray()
        }

        return arrayOf(
                (-b - sqrt(discriminant)) / (2.0 * a),
                (-b + sqrt(discriminant)) / (2.0 * a)
        )
    }

}