package raytracer

import org.junit.jupiter.api.Test
import java.io.File

internal class Chapter05Test {

    @Test
    fun `Cast rays at a sphere book suggestion`() {
        val rayOrigin = Tuple.point(0.0, 0.0, -5.0)
        val wallZ = 10.0
        val wallSize = 7.0
        val canvasPixels = 100
        val pixelSize = wallSize / canvasPixels
        val half = wallSize / 2
        println("Pixel size is $pixelSize, half is $half")

        val canvas = Canvas(canvasPixels, canvasPixels)
        val shape = Sphere()
//        shape.transform = Transformations.scaling(1.0, 0.5, 1.0)
//        shape.transform = Matrices.multiply(Transformations.rotationZ(PI / 4), Transformations.scaling(0.5, 1.0, 1.0))
//        shape.transform = Matrices.multiply(Transformations.shearing(1.0, 0.0, 0.0, 0.0, 0.0, 0.0), Transformations.scaling(0.5, 1.0, 1.0))

//        val colors = listOf(Colors.RED, Colors.GREEN, Colors.BLUE)
        val colors = listOf(Colors.WHITE, Colors.RED, Colors.GREEN, Colors.BLACK)

        for (y in 0 until canvasPixels) {
            val worldY = half - pixelSize * y

            for (x in 0 until canvasPixels) {
                val worldX = -half + pixelSize * x
                val position = Tuple.point(worldX, worldY, wallZ)
                val ray = Ray(rayOrigin, (position - rayOrigin).normalize())
                val intersections = shape.intersect(ray)

                val hit = intersections.hit();
                if (hit != null) {
                    var colorIndex = getBackgroundColor(worldX, worldY) + 1
                    if (colorIndex == colors.size) {
                        colorIndex = 0;
                    }
                    canvas.writePixel(x, y, colors[colorIndex])
                } else {
                    canvas.writePixel(x, y, colors[getBackgroundColor(worldX, worldY)])
                }
                println("y=$y x=$x = point $position = intersections $intersections = hit $hit")
            }
        }
        File("/tmp/canvas.ppm").writeText(canvas.toPPM())
    }


    @Test
    fun `Cast rays at a sphere book suggestion with shading`() {
        val rayOrigin = Tuple.point(0.0, 0.0, -5.0)
        val wallZ = 10.0
        val wallSize = 7.0
        val canvasPixels = 100
        val pixelSize = wallSize / canvasPixels
        val half = wallSize / 2
        println("Pixel size is $pixelSize, half is $half")

        val canvas = Canvas(canvasPixels, canvasPixels)
        val shape = Sphere()
        shape.material = Material()
        shape.material.color = Tuple.color(1.0, 0.2, 1.0)
        val lightPosition = Tuple.point(-10.0, 10.0, -10.0)
        val lightColor = Colors.WHITE
        val light = PointLight(lightPosition, lightColor)


//        shape.transform = Transformations.scaling(1.0, 0.5, 1.0)
//        shape.transform = Matrices.multiply(Transformations.rotationZ(PI / 4), Transformations.scaling(0.5, 1.0, 1.0))
//        shape.transform = Matrices.multiply(Transformations.shearing(1.0, 0.0, 0.0, 0.0, 0.0, 0.0), Transformations.scaling(0.5, 1.0, 1.0))

//        val colors = listOf(Colors.RED, Colors.GREEN, Colors.BLUE)
        val colors = listOf(Colors.WHITE, Colors.RED, Colors.GREEN, Colors.BLACK)

        for (y in 0 until canvasPixels) {
            val worldY = half - pixelSize * y

            for (x in 0 until canvasPixels) {
                val worldX = -half + pixelSize * x
                val position = Tuple.point(worldX, worldY, wallZ)
                val ray = Ray(rayOrigin, (position - rayOrigin).normalize())
                val intersections = shape.intersect(ray)

                val hit = intersections.hit();
                if (hit != null) {
                    val point = ray.position(hit.time)
                    val normal = hit.obj.normalAt(point)
                    val eye = -ray.direction
                    val color = Material.lighting(hit.obj.material, light, point, eye, normal)

                    canvas.writePixel(x, y, color)
                } else {
                    canvas.writePixel(x, y, colors[getBackgroundColor(worldX, worldY)])
                }
                println("y=$y x=$x = point $position = intersections $intersections = hit $hit")
            }
        }
        File("/tmp/canvas.ppm").writeText(canvas.toPPM())
    }

    private fun getBackgroundColor(worldX: Double, worldY: Double): Int {
        return if (worldX < 0.0) {
            if (worldY > 0.0) {
                0
            } else {
                2
            }
        } else {
            if (worldY > 0.0) {
                1
            } else {
                3
            }
        }
    }
}