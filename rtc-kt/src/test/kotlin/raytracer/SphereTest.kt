package raytracer

import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test
import kotlin.math.PI
import kotlin.math.sqrt

internal class SphereTest {

    @Test
    fun `A ray intersects a sphere at two points`() {
        val ray = Ray(Tuple.point(0.0, 0.0, -5.0), Tuple.vector(0.0, 0.0, 1.0))
        val sphere = Sphere()

        val xs = sphere.intersect(ray)

        xs.size shouldBe 2
        xs.list[0].time shouldBe 4.0
        xs.list[0].obj shouldBe sphere
        xs.list[1].time shouldBe 6.0
        xs.list[1].obj shouldBe sphere
    }

    @Test
    fun `A ray intersects a sphere at a tangent`() {
        val ray = Ray(Tuple.point(0.0, 1.0, -5.0), Tuple.vector(0.0, 0.0, 1.0))
        val sphere = Sphere()

        val xs = sphere.intersect(ray)

        xs.size shouldBe 2
        xs.list[0].time shouldBe 5.0
        xs.list[0].obj shouldBe sphere
        xs.list[1].time shouldBe 5.0
        xs.list[1].obj shouldBe sphere
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
        xs.list[0].time shouldBe -1.0
        xs.list[0].obj shouldBe sphere
        xs.list[1].time shouldBe 1.0
        xs.list[1].obj shouldBe sphere
    }

    @Test
    fun `A ray is behind a ray`() {
        val ray = Ray(Tuple.point(0.0, 0.0, 5.0), Tuple.vector(0.0, 0.0, 1.0))
        val sphere = Sphere()

        val xs = sphere.intersect(ray)

        xs.size shouldBe 2
        xs.list[0].time shouldBe -6.0
        xs.list[0].obj shouldBe sphere
        xs.list[1].time shouldBe -4.0
        xs.list[1].obj shouldBe sphere
    }

    @Test
    fun `Intersect sets the object on the intersection`() {
        val ray = Ray(Tuple.point(0.0, 0.0, -5.0), Tuple.vector(0.0, 0.0, 1.0))
        val sphere = Sphere()

        val xs = sphere.intersect(ray)

        xs.size shouldBe 2
        xs.list[0].obj shouldBe sphere
        xs.list[1].obj shouldBe sphere
    }

    @Test
    fun `A sphere's default transformation`() {
        val sphere = Sphere()

        sphere.transform shouldBe Matrices.IDENTITY
    }

    @Test
    fun `Changing a sphere's transformation`() {
        val sphere = Sphere()
        val translation = Transformations.translation(2.0, 3.0, 4.0)
        sphere.transform = translation

        sphere.transform contentDeepEquals Transformations.translation(2.0, 3.0, 4.0) shouldBe true
    }

    @Test
    fun `Intersecting a scaled sphere with a ray`() {
        val ray = Ray(Tuple.point(0.0, 0.0, -5.0), Tuple.vector(0.0, 0.0, 1.0))
        val sphere = Sphere()

        sphere.transform = Transformations.scaling(2.0, 2.0, 2.0)

        val intersections = sphere.intersect(ray)
        intersections.size shouldBe 2
        intersections.list[0].time shouldBe 3.0
        intersections.list[1].time shouldBe 7.0
    }

    @Test
    fun `Intersecting a translated sphere with a ray`() {
        val ray = Ray(Tuple.point(0.0, 0.0, -5.0), Tuple.vector(0.0, 0.0, 1.0))
        val sphere = Sphere()

        sphere.transform = Transformations.translation(5.0, 0.0, 0.0)

        val intersections = sphere.intersect(ray)
        intersections.size shouldBe 0
    }

    @Test
    fun `The normal on a sphere at a point on the x axis`() {
        val sphere = Sphere()
        val point = Tuple.point(1.0, 0.0, 0.0)

        val vector = sphere.normalAt(point)

        vector shouldBe Tuple.vector(1.0, 0.0, 0.0)
    }

    @Test
    fun `The normal on a sphere at a point on the y axis`() {
        val sphere = Sphere()
        val point = Tuple.point(0.0, 1.0, 0.0)

        val vector = sphere.normalAt(point)

        vector shouldBe Tuple.vector(0.0, 1.0, 0.0)
    }

    @Test
    fun `The normal on a sphere at a point on the z axis`() {
        val sphere = Sphere()
        val point = Tuple.point(0.0, 0.0, 1.0)

        val vector = sphere.normalAt(point)

        vector shouldBe Tuple.vector(0.0, 0.0, 1.0)
    }

    @Test
    fun `The normal on a sphere at a nonaxial point`() {
        val sphere = Sphere()
        val point = Tuple.point(sqrt(3.0) / 3.0, sqrt(3.0) / 3.0, sqrt(3.0) / 3.0)

        val vector = sphere.normalAt(point)

        vector shouldBe Tuple.vector(sqrt(3.0) / 3.0, sqrt(3.0) / 3.0, sqrt(3.0) / 3.0)
    }

    @Test
    fun `The normal is a normalized vector`() {
        val sphere = Sphere()
        val point = Tuple.point(sqrt(3.0) / 3.0, sqrt(3.0) / 3.0, sqrt(3.0) / 3.0)

        val vector = sphere.normalAt(point)

        vector shouldBe vector.normalize()
    }

    @Test
    fun `Computing the normal on a translated sphere`() {
        val sphere = Sphere()
        sphere.transform = Transformations.translation(0.0, 1.0, 0.0)
        val point = Tuple.point(0.0, 1.70711, -0.70711)

        val vector = sphere.normalAt(point)

        vector shouldBe Tuple.vector(0.0, 0.7071067811865475, -0.7071067811865476)
    }

    @Test
    fun `Computing the normal on a transformed sphere`() {
        val sphere = Sphere()
        sphere.transform = Matrices.multiply(Transformations.scaling(1.0, 0.5, 1.0), Transformations.rotationZ(PI / 5))
        val point = Tuple.point(0.0, sqrt(2.0) / 2.0, -sqrt(2.0) / 2.0)

        val vector = sphere.normalAt(point)

        vector shouldBe Tuple.vector(0.0, 0.9701425001453319, -0.24253562503633294)
    }

}