package raytracer

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.math.PI

internal class Chapter04 {

    @Test
    fun clock() {
        val colors = listOf(Colors.RED, Colors.GREEN, Colors.BLUE, Colors.WHITE)

        val canvas = Canvas(500, 500)

        val center = Tuple.point(canvas.width / 2.0, 0.0, canvas.height / 2.0)
        val initialPoint = Tuple.point(0.0, 0.0, 1.0)

        for (i in 1..12) {
            val transformation = Transformations.rotationY(i * (PI/6.0))
            // rotate on y, scale 3/8 of width and move to center
            val point = Matrices.multiply(transformation, initialPoint) * (3.0 / 8.0 * canvas.width) + center

            println("$i ${point.x} ${point.z}")
            canvas.writePixel(point.x.toInt(), point.z.toInt(), colors.shuffled().first())
        }

        File("/tmp/canvas.ppm").writeText(canvas.toPPM())
    }
}