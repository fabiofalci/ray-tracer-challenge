package raytracer

import kotlin.math.roundToInt

class Canvas(private val width: Int, private val height: Int) {

    private val matrix: Array<Array<Tuple>> = Array(width) {
        Array(height) {
            Colors.WHITE
        }
    }

    fun getPixel(x: Int, y: Int): Tuple {
        return matrix[x][y]

    }

    fun writePixel(x: Int, y: Int, color: Tuple) {
        try {
            matrix[x][y] = color
        } catch (e: ArrayIndexOutOfBoundsException) {
            // ignoring out of bounds pixels
        }
    }

    fun toPPM(): String {
        val body = StringBuilder()
        for (y in 0 until height) {
            val line = StringBuilder()
            var offset = 0
            for (x in 0 until width) {
                val color = matrix[x][y]
                offset = appendColor(line, color.x, offset)
                offset = appendColor(line, color.y, offset)
                line.append(scale(color.z))
                offset = checkLimit(line, offset)
                if (x != width - 1) {
                    line.append(" ")
                }
            }
            body.append(line).append("\n")
        }
        return "P3\n$width $height\n255\n$body"
    }

    private fun appendColor(line: StringBuilder, color: Double, offset: Int): Int {
        line.append(scale(color)).append(" ")
        return checkLimit(line, offset)
    }

    private fun checkLimit(line: StringBuilder, offset: Int): Int {
        if (line.length - offset > 70) {
            val lastWhitespace = line.lastIndexOf(" ")
            line.replace(lastWhitespace, lastWhitespace + 1, "\n")
            return lastWhitespace
        }
        return offset
    }

    private fun scale(number: Double): Int {
        val scaled = (255 * number).roundToInt()
        return if (scaled > 255) 255 else if (scaled < 0) 0 else scaled
    }
}