package raytracer

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.math.PI

internal class Chapter05Test {

    @Test
    fun `Cast rays at a sphere`() {
        val canvas = Canvas(500, 500)
        val sphere = Sphere()

        val translation = Transformations.translation(250.0, 250.0, 10.0)
        val scaling = Transformations.scaling(230.0, 230.0, 5.0)
        sphere.transform = Matrices.multiply(translation, scaling)

        for (x in 0..canvas.width) {
            for (y in 0..canvas.height) {
                val ray = Ray(Tuple.point(x.toDouble(), y.toDouble(), -20.0), Tuple.vector(0.0, 0.0, 2.0))
                val intersections = sphere.intersect(ray)
                val hit = intersections.hit()
                if (hit != null) {
                    canvas.writePixel(x, y, Colors.RED)
                }
            }
        }

        File("/tmp/canvas.ppm").writeText(canvas.toPPM())
    }
}