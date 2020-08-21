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

    @Test
    fun `Translating a ray`() {
        val ray = Ray(Tuple.point(1.0, 2.0, 3.0), Tuple.vector(0.0, 1.0, 0.0))
        val translation = Transformations.translation(3.0, 4.0, 5.0)
        val ray2 = ray.transform(translation)

        ray2.origin shouldBe Tuple.point(4.0, 6.0, 8.0)
        ray2.direction shouldBe Tuple.vector(0.0, 1.0, 0.0)
    }

    @Test
    fun `Scaling a ray`() {
        val ray = Ray(Tuple.point(1.0, 2.0, 3.0), Tuple.vector(0.0, 1.0, 0.0))
        val translation = Transformations.scaling(2.0, 3.0, 4.0)
        val ray2 = ray.transform(translation)

        ray2.origin shouldBe Tuple.point(2.0, 6.0, 12.0)
        ray2.direction shouldBe Tuple.vector(0.0, 3.0, 0.0)
    }
}