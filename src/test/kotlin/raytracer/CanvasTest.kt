package raytracer

import io.kotlintest.matchers.string.shouldEndWith
import io.kotlintest.matchers.string.shouldStartWith
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test
import raytracer.Colors.RED

internal class CanvasTest {

    @Test
    fun `Creating a canvas`() {
        val canvas = Canvas(10, 20)

        for (x in 0 until 10) {
            for (y in 0 until 20) {
                canvas.getPixel(x, y) shouldBe Colors.WHITE
            }
        }
    }

    @Test
    fun `Writing pixels to a canvas`() {
        val canvas = Canvas(10, 20)

        canvas.writePixel(2, 3, RED)

        canvas.getPixel(2, 3) shouldBe RED
    }

    @Test
    fun `Constructing the PPM header`() {
        val canvas = Canvas(5, 3)

        val ppm = canvas.toPPM()

        ppm shouldStartWith """
            P3
            5 3
            255
        """.trimIndent()
    }

    @Test
    fun `Constructing the PPM pixel data`() {
        val canvas = Canvas(5, 3)

        canvas.writePixel(0, 0, Tuple.color(1.5, 0.0, 0.0))
        canvas.writePixel(2, 1, Tuple.color(0.0, 0.5, 0.0))
        canvas.writePixel(4, 2, Tuple.color(-0.5, 0.0, 1.0))

        val ppm = canvas.toPPM()

        ppm shouldEndWith  """
            255 0 0 0 0 0 0 0 0 0 0 0 0 0 0
            0 0 0 0 0 0 0 128 0 0 0 0 0 0 0
            0 0 0 0 0 0 0 0 0 0 0 0 0 0 255
        """.trimIndent().plus("\n")
    }

    @Test
    fun `Splitting long lines in PPM files`() {
        val canvas = Canvas(10, 2)

        for (x in 0 until 10) {
            for (y in 0 until 2) {
                canvas.writePixel(x, y, Tuple.color(1.0, 0.8, 0.6))
            }
        }

        val ppm = canvas.toPPM()

        ppm shouldEndWith  """
            255 204 153 255 204 153 255 204 153 255 204 153 255 204 153 255 204
            153 255 204 153 255 204 153 255 204 153 255 204 153
            255 204 153 255 204 153 255 204 153 255 204 153 255 204 153 255 204
            153 255 204 153 255 204 153 255 204 153 255 204 153
        """.trimIndent().plus("\n")
    }

    @Test
    fun `PPM files are terminated by a new character`() {
        val canvas = Canvas(5, 3)

        val ppm = canvas.toPPM()

        ppm shouldEndWith "\n"
    }
}