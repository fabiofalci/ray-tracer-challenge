package raytracer

import org.junit.jupiter.api.Test

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
}