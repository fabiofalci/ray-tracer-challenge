package raytracer

import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test
import kotlin.math.PI
import kotlin.math.sqrt

internal class TransformationsTest {

    @Test
    fun `Multiplying by a translation matrix`() {
        val transform = Transformations.translation(5.0, -3.0, 2.0)
        val point = Tuple.point(-3.0, 4.0, 5.0)

        Matrices.multiply(transform, point) shouldBe Tuple.point(2.0, 1.0, 7.0)
    }

    @Test
    fun `Multiplying by the inverse of a translation matrix`() {
        val transform = Matrices.inverse(Transformations.translation(5.0, -3.0, 2.0))
        val point = Tuple.point(-3.0, 4.0, 5.0)

        Matrices.multiply(transform, point) shouldBe Tuple.point(-8.0, 7.0, 3.0)
    }

    @Test
    fun `Translations does not affect vectors`() {
        val transform = Transformations.translation(5.0, -3.0, 2.0)
        val vector = Tuple.vector(-3.0, 4.0, 5.0)

        Matrices.multiply(transform, vector) shouldBe vector
    }

    @Test
    fun `A scaling matrix applied to a point`() {
        val transform = Transformations.scaling(2.0, 3.0, 4.0)
        val point = Tuple.point(-4.0, 6.0, 8.0)

        Matrices.multiply(transform, point) shouldBe Tuple.point(-8.0, 18.0, 32.0)
    }

    @Test
    fun `A scaling matrix applied to a vector`() {
        val transform = Transformations.scaling(2.0, 3.0, 4.0)
        val vector = Tuple.vector(-4.0, 6.0, 8.0)

        Matrices.multiply(transform, vector) shouldBe Tuple.vector(-8.0, 18.0, 32.0)
    }

    @Test
    fun `Multiplying by the inverse of a scaling matrix`() {
        val transform = Matrices.inverse(Transformations.scaling(2.0, 3.0, 4.0))
        val vector = Tuple.vector(-4.0, 6.0, 8.0)

        Matrices.multiply(transform, vector) shouldBe Tuple.vector(-2.0, 2.0, 2.0)
    }

    @Test
    fun `Reflection is scaling by a negative value`() {
        val transform = Transformations.scaling(-1.0, 1.0, 1.0)
        val point = Tuple.point(2.0, 3.0, 4.0)

        Matrices.multiply(transform, point) shouldBe Tuple.point(-2.0, 3.0, 4.0)
    }

    @Test
    fun `Rotating a point around the x axis`() {
        val point = Tuple.point(0.0, 1.0, 0.0)
        val halfQuarter = Transformations.rotationX(PI / 4)
        val fullQuarter = Transformations.rotationX(PI / 2)

        Matrices.multiply(halfQuarter, point) shouldBe Tuple.point(0.0, sqrt(2.0)/2, sqrt(2.0)/2)
        Matrices.multiply(fullQuarter, point) shouldBe Tuple.point(0.0, 0.0, 1.0)
    }

    @Test
    fun `The inverse of an x rotation rotates in the opposite direction`() {
        val point = Tuple.point(0.0, 1.0, 0.0)
        val halfQuarter = Matrices.inverse(Transformations.rotationX(Math.PI / 4))

        Matrices.multiply(halfQuarter, point) shouldBe Tuple.point(0.0, sqrt(2.0)/2, -sqrt(2.0)/2)
    }

    @Test
    fun `Rotating a point around the y axis`() {
        val point = Tuple.point(0.0, 0.0, 1.0)
        val halfQuarter = Transformations.rotationY(Math.PI / 4)
        val fullQuarter = Transformations.rotationY(Math.PI / 2)

        Matrices.multiply(halfQuarter, point) shouldBe Tuple.point(sqrt(2.0)/2, 0.0, sqrt(2.0)/2)
        Matrices.multiply(fullQuarter, point) shouldBe Tuple.point(1.0, 0.0, 0.0)
    }

    @Test
    fun `Rotating a point around the z axis`() {
        val point = Tuple.point(0.0, 1.0, 0.0)
        val halfQuarter = Transformations.rotationZ(Math.PI / 4)
        val fullQuarter = Transformations.rotationZ(Math.PI / 2)

        Matrices.multiply(halfQuarter, point) shouldBe Tuple.point(-sqrt(2.0)/2, sqrt(2.0)/2, 0.0)
        Matrices.multiply(fullQuarter, point) shouldBe Tuple.point(-1.0, 0.0, 0.0)
    }

    @Test
    fun `A shearing transformation moves x in proportion to y`() {
        val transform = Transformations.shearing(1.0, 0.0, 0.0, 0.0, 0.0, 0.0)
        val point = Tuple.point(2.0, 3.0, 4.0)

        Matrices.multiply(transform, point) shouldBe Tuple.point(5.0, 3.0, 4.0)
    }

    @Test
    fun `A shearing transformation moves x in proportion to z`() {
        val transform = Transformations.shearing(0.0, 1.0, 0.0, 0.0, 0.0, 0.0)
        val point = Tuple.point(2.0, 3.0, 4.0)

        Matrices.multiply(transform, point) shouldBe Tuple.point(6.0, 3.0, 4.0)
    }

    @Test
    fun `A shearing transformation moves y in proportion to x`() {
        val transform = Transformations.shearing(0.0, 0.0, 1.0, 0.0, 0.0, 0.0)
        val point = Tuple.point(2.0, 3.0, 4.0)

        Matrices.multiply(transform, point) shouldBe Tuple.point(2.0, 5.0, 4.0)
    }

    @Test
    fun `A shearing transformation moves y in proportion to z`() {
        val transform = Transformations.shearing(0.0, 0.0, 0.0, 1.0, 0.0, 0.0)
        val point = Tuple.point(2.0, 3.0, 4.0)

        Matrices.multiply(transform, point) shouldBe Tuple.point(2.0, 7.0, 4.0)
    }

    @Test
    fun `A shearing transformation moves z in proportion to x`() {
        val transform = Transformations.shearing(0.0, 0.0, 0.0, 0.0, 1.0, 0.0)
        val point = Tuple.point(2.0, 3.0, 4.0)

        Matrices.multiply(transform, point) shouldBe Tuple.point(2.0, 3.0, 6.0)
    }

    @Test
    fun `A shearing transformation moves z in proportion to y`() {
        val transform = Transformations.shearing(0.0, 0.0, 0.0, 0.0, 0.0, 1.0)
        val point = Tuple.point(2.0, 3.0, 4.0)

        Matrices.multiply(transform, point) shouldBe Tuple.point(2.0, 3.0, 7.0)
    }

    @Test
    fun `Individual transformations are applied in sequence`() {
        val point = Tuple.point(1.0, 0.0, 1.0)
        val a = Transformations.rotationX(PI/2.0)
        val b = Transformations.scaling(5.0, 5.0, 5.0)
        val c = Transformations.translation(10.0, 5.0, 7.0)

        // rotation
        val p2 = Matrices.multiply(a, point)
        p2 shouldBe Tuple.point(1.0, -1.0, 0.0)

        // scaling
        val p3 = Matrices.multiply(b, p2)
        p3 shouldBe Tuple.point(5.0, -5.0, 0.0)

        // translation
        val p4 = Matrices.multiply(c, p3)
        p4 shouldBe Tuple.point(15.0, 0.0, 7.0)

    }

    @Test
    fun `Chained transformations must be applied in reverse order`() {
        val point = Tuple.point(1.0, 0.0, 1.0)
        val a = Transformations.rotationX(PI/2.0)
        val b = Transformations.scaling(5.0, 5.0, 5.0)
        val c = Transformations.translation(10.0, 5.0, 7.0)

        val transform = Matrices.multiply(Matrices.multiply(c, b), a)
        Matrices.multiply(transform, point) shouldBe Tuple.point(15.0, 0.0, 7.0)
    }

}