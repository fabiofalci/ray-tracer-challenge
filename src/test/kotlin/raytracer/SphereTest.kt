package raytracer

import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

internal class SphereTest {

    @Test
    fun `A ray intersects a sphere at two points`() {
        val ray = Ray(Tuple.point(0.0, 0.0, -5.0), Tuple.vector(0.0, 0.0, 1.0))
        val sphere = Sphere()

        val xs = sphere.intersect(ray)

        xs.size shouldBe 2
        xs[0] shouldBe 4.0
        xs[1] shouldBe 6.0
    }

    @Test
    fun `A ray intersects a sphere at a tangent`() {
        val ray = Ray(Tuple.point(0.0, 1.0, -5.0), Tuple.vector(0.0, 0.0, 1.0))
        val sphere = Sphere()

        val xs = sphere.intersect(ray)

        xs.size shouldBe 2
        xs[0] shouldBe 5.0
        xs[1] shouldBe 5.0
    }

    @Test
    fun `A ray misses a sphere`() {
        val ray = Ray(Tuple.point(0.0, 2.0, -5.0), Tuple.vector(0.0, 0.0, 1.0))
        val sphere = Sphere()

        val xs = sphere.intersect(ray)

        xs.size shouldBe 0
    }

    @Test
    fun `A ray originates inside a sphere`() {
        val ray = Ray(Tuple.point(0.0, 0.0, 0.0), Tuple.vector(0.0, 0.0, 1.0))
        val sphere = Sphere()

        val xs = sphere.intersect(ray)

        xs.size shouldBe 2
        xs[0] shouldBe -1.0
        xs[1] shouldBe 1.0
    }

    @Test
    fun `A ray is behind a ray`() {
        val ray = Ray(Tuple.point(0.0, 0.0, 5.0), Tuple.vector(0.0, 0.0, 1.0))
        val sphere = Sphere()

        val xs = sphere.intersect(ray)

        xs.size shouldBe 2
        xs[0] shouldBe -6.0
        xs[1] shouldBe -4.0
    }
}