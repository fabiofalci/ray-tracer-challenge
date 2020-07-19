package raytracer

import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

internal class IntersectionTest {

    @Test
    fun `An intersection encapsulates t and object`() {
        val sphere = Sphere()
        val intersection = Intersection(3.5, sphere)

        intersection.time shouldBe 3.5
        intersection.obj shouldBe sphere
    }

    @Test
    fun `Aggregating intersection`() {
        val sphere = Sphere()
        val i1 = Intersection(1.0, sphere)
        val i2 = Intersection(2.0, sphere)

        val xs = Intersection.intersections(i1, i2)

        xs.size shouldBe 2

        xs[0].time shouldBe 1.0
        xs[1].time shouldBe 2.0
    }
}