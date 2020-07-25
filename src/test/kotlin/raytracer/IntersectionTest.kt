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

        val xs = Intersections(i1, i2)

        xs.size shouldBe 2

        xs.list[0].time shouldBe 1.0
        xs.list[1].time shouldBe 2.0
    }

    @Test
    fun `The hit when all intersections have positive t`() {
        val sphere = Sphere()
        val i1 = Intersection(1.0, sphere)
        val i2 = Intersection(2.0, sphere)

        val xs = Intersections(i2, i1)

        xs.hit() shouldBe i1
    }

    @Test
    fun `The hit when some intersections have negative t`() {
        val sphere = Sphere()
        val i1 = Intersection(-1.0, sphere)
        val i2 = Intersection(1.0, sphere)

        val xs = Intersections(i2, i1)

        xs.hit() shouldBe i2
    }

    @Test
    fun `The hit when all intersections have negative t`() {
        val sphere = Sphere()
        val i1 = Intersection(-2.0, sphere)
        val i2 = Intersection(-1.0, sphere)

        val xs = Intersections(i2, i1)

        xs.hit() shouldBe null
    }

    @Test
    fun `The hit is always the lowest nonnegative intersection`() {
        val sphere = Sphere()
        val i1 = Intersection(5.0, sphere)
        val i2 = Intersection(7.0, sphere)
        val i3 = Intersection(-3.0, sphere)
        val i4 = Intersection(2.0, sphere)

        val xs = Intersections(i1, i2, i3, i4)

        xs.hit() shouldBe i4
    }
}