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
        xs[0].time shouldBe 4.0
        xs[0].obj shouldBe sphere
        xs[1].time shouldBe 6.0
        xs[1].obj shouldBe sphere
    }

    @Test
    fun `A ray intersects a sphere at a tangent`() {
        val ray = Ray(Tuple.point(0.0, 1.0, -5.0), Tuple.vector(0.0, 0.0, 1.0))
        val sphere = Sphere()

        val xs = sphere.intersect(ray)

        xs.size shouldBe 2
        xs[0].time shouldBe 5.0
        xs[0].obj shouldBe sphere
        xs[1].time shouldBe 5.0
        xs[1].obj shouldBe sphere
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
        xs[0].time shouldBe -1.0
        xs[0].obj shouldBe sphere
        xs[1].time shouldBe 1.0
        xs[1].obj shouldBe sphere
    }

    @Test
    fun `A ray is behind a ray`() {
        val ray = Ray(Tuple.point(0.0, 0.0, 5.0), Tuple.vector(0.0, 0.0, 1.0))
        val sphere = Sphere()

        val xs = sphere.intersect(ray)

        xs.size shouldBe 2
        xs[0].time shouldBe -6.0
        xs[0].obj shouldBe sphere
        xs[1].time shouldBe -4.0
        xs[1].obj shouldBe sphere
    }

    @Test
    fun `Intersect sets the object on the intersection`() {
        val ray = Ray(Tuple.point(0.0, 0.0, -5.0), Tuple.vector(0.0, 0.0, 1.0))
        val sphere = Sphere()

        val xs = sphere.intersect(ray)

        xs.size shouldBe 2
        xs[0].obj shouldBe sphere
        xs[1].obj shouldBe sphere
    }
}