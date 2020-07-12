package raytracer

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.math.roundToInt

data class Projectile(val position: Tuple, val velocity: Tuple)
data class Environment(val gravity: Tuple, val wind: Tuple)

fun tick(environment: Environment, projectile: Projectile): Projectile {
    return Projectile(
            projectile.position + projectile.velocity,
            projectile.velocity + environment.gravity + environment.wind
    )
}

internal class Chapter01Test {

    @Test
    fun test() {
        var projectile = Projectile(position = Tuple.point(0.0, 1.0, 0.0), velocity = Tuple.vector(1.0, 1.0, 0.0).normalize())
        val environment = Environment(gravity = Tuple.vector(0.0, -0.1, 0.0), wind = Tuple.vector(-0.01, 0.0, 0.0))

        println("Staring point ${projectile.position}")
        var count = 0

        while (projectile.position.y > 0) {
            count++
            projectile = tick(environment, projectile)
            println("$count: ${projectile.position}")
        }
    }

    @Test
    fun plotToPPM() {
        var projectile = Projectile(position = Tuple.point(0.0, 1.0, 0.0), velocity = Tuple.vector(1.0, 1.8, 0.0).normalize() * 11.25)
        val environment = Environment(gravity = Tuple.vector(0.0, -0.1, 0.0), wind = Tuple.vector(-0.01, 0.0, 0.0))
        var canvas = Canvas(900, 500)
        canvas.writePixel(projectile.position.x.roundToInt(), 500 - projectile.position.y.roundToInt(), Colors.RED)

        var count = 0

        while (projectile.position.y > 0) {
            count++
            projectile = tick(environment, projectile)
            canvas.writePixel(projectile.position.x.roundToInt(), 500 - projectile.position.y.roundToInt(), Colors.RED)
        }

        File("/tmp/canvas.ppm").writeText(canvas.toPPM())
    }
}