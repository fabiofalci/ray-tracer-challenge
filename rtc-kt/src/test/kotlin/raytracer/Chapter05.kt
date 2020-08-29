package raytracer

import org.junit.jupiter.api.Test
import java.io.File
import java.lang.Math.PI

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

    @Test
    fun `Cast rays at a sphere book suggestion`() {
        val rayOrigin = Tuple.point(0.0, 0.0, -5.0)
        val wallZ = 10.0
        val wallSize = 8.0
        val canvasPixels = 200
        val pixelSize = wallSize / canvasPixels
        val half = wallSize / 2

        val canvas = Canvas(canvasPixels, canvasPixels)
        val shape = Sphere()
//        shape.transform = Transformations.scaling(1.0, 0.5, 1.0)
//        shape.transform = Matrices.multiply(Transformations.rotationZ(PI / 4), Transformations.scaling(0.5, 1.0, 1.0))
        shape.transform = Matrices.multiply(Transformations.shearing(1.0, 0.0, 0.0, 0.0, 0.0, 0.0), Transformations.scaling(0.5, 1.0, 1.0))

        for (y in 0 until canvasPixels) {
            val worldY = half - pixelSize * y

            for (x in 0 until canvasPixels) {
                val worldX = -half + pixelSize * x
                val position = Tuple.point(worldX, worldY, wallZ)
                val ray = Ray(rayOrigin, (position - rayOrigin).normalize())
                val intersections = shape.intersect(ray)

                if (intersections.hit() != null) {
                    canvas.writePixel(x, y, Colors.RED)
                }
            }
        }
        File("/tmp/canvas.ppm").writeText(canvas.toPPM())
    }
}