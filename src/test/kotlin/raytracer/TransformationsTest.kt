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

}