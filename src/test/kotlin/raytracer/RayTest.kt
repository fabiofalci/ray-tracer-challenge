package raytracer

import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

internal class RayTest {

    @Test
    fun `Creating and querying a ray`() {
        val origin = Tuple.point(1.0, 2.0, 3.0)
        val direction = Tuple.vector(4.0, 5.0, 6.0)

        val ray = Ray(origin, direction)

        ray.origin shouldBe origin
        ray.direction shouldBe direction
    }

    @Test
    fun `Computing a point from a distance`() {
        val ray = Ray(Tuple.point(2.0, 3.0, 4.0), Tuple.vector(1.0, 0.0, 0.0))

        ray.position(0.0) shouldBe Tuple.point(2.0, 3.0, 4.0)
        ray.position(1.0) shouldBe Tuple.point(3.0, 3.0, 4.0)
        ray.position(-1.0) shouldBe Tuple.point(1.0, 3.0, 4.0)
        ray.position(2.5) shouldBe Tuple.point(4.5, 3.0, 4.0)

    }
}