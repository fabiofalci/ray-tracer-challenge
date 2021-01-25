package raytracer

import io.kotlintest.matchers.string.shouldStartWith
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.shouldThrow
import org.junit.jupiter.api.Test
import kotlin.math.sqrt

internal class TupleTest {

    @Test
    fun `A tuple with w 1 is a point`() {
        val tuple = Tuple(4.3, -4.2, 3.1, 1)

        tuple.isPoint() shouldBe true
        tuple.isVector() shouldBe false
    }

    @Test
    fun `A tuple with w 0 is a point`() {
        val tuple = Tuple(4.3, -4.2, 3.1, 0)

        tuple.isPoint() shouldBe false
        tuple.isVector() shouldBe true
    }

    @Test
    fun `Point factory method`() {
        val tuple = Tuple.point(4.3, -4.2, 3.1)

        tuple.isPoint() shouldBe true
        tuple.isVector() shouldBe false
        tuple.w shouldBe 1
    }

    @Test
    fun `Vector factory method`() {
        val tuple = Tuple.vector(4.3, -4.2, 3.1)

        tuple.isPoint() shouldBe false
        tuple.isVector() shouldBe true
        tuple.w shouldBe 0
    }

    @Test
    fun `Vector equality`() {
        Tuple.vector(4.3, -4.2, 3.1) shouldBe Tuple.vector(4.3, -4.2, 3.1)
        Tuple.vector(4.4, -4.2, 3.1) shouldNotBe Tuple.vector(4.3, -4.2, 3.1)
        Tuple.vector(4.3, -4.3, 3.1) shouldNotBe Tuple.vector(4.3, -4.2, 3.1)
        Tuple.vector(4.3, -4.2, 3.2) shouldNotBe Tuple.vector(4.3, -4.2, 3.1)
    }

    @Test
    fun `Point equality`() {
        Tuple.point(4.3, -4.2, 3.1) shouldBe Tuple.point(4.3, -4.2, 3.1)
        Tuple.point(4.4, -4.2, 3.1) shouldNotBe Tuple.point(4.3, -4.2, 3.1)
        Tuple.point(4.3, -4.3, 3.1) shouldNotBe Tuple.point(4.3, -4.2, 3.1)
        Tuple.point(4.3, -4.2, 3.2) shouldNotBe Tuple.point(4.3, -4.2, 3.1)

        Tuple.point(4.3, -4.2, 3.1) shouldNotBe Tuple.vector(4.3, -4.2, 3.1)
    }

    @Test
    fun `Adding two tuples`() {
        Tuple.point(3.0, -2.0, 5.0) + (Tuple.vector(-2.0, 3.0, 1.0)) shouldBe Tuple.point(1.0, 1.0, 6.0)
        Tuple.vector(3.0, -2.0, 5.0) + (Tuple.point(-2.0, 3.0, 1.0)) shouldBe Tuple.point(1.0, 1.0, 6.0)
        Tuple.vector(3.0, -2.0, 5.0) + (Tuple.vector(-2.0, 3.0, 1.0)) shouldBe Tuple.vector(1.0, 1.0, 6.0)

        val exception = shouldThrow<RuntimeException> { Tuple.point(3.0, -2.0, 5.0) + Tuple.point(-2.0, 3.0, 1.0) }
        exception.message shouldStartWith "Cannot add two points"
    }

    @Test
    fun `Subtracting two tuples`() {
        Tuple.point(3.0, 2.0, 1.0) - (Tuple.point(5.0, 6.0, 7.0)) shouldBe Tuple.vector(-2.0, -4.0, -6.0)
        Tuple.point(3.0, 2.0, 1.0) - (Tuple.vector(5.0, 6.0, 7.0)) shouldBe Tuple.point(-2.0, -4.0, -6.0)
        Tuple.vector(3.0, 2.0, 1.0) - (Tuple.vector(5.0, 6.0, 7.0)) shouldBe Tuple.vector(-2.0, -4.0, -6.0)

        val exception = shouldThrow<RuntimeException> { Tuple.vector(3.0, -2.0, 5.0) - Tuple.point(-2.0, 3.0, 1.0) }
        exception.message shouldStartWith "Cannot subtract a point from a vector"
    }

    @Test
    fun `Negating a tuple`() {
        -Tuple(3.0, -2.0, 1.0, -7) shouldBe Tuple(-3.0, 2.0, -1.0, 7)
    }

    @Test
    fun `Multiplying a tuple by a scalar and fraction`() {
        Tuple(1.0, -2.0, 3.0, -4) * 3.5 shouldBe Tuple(3.5, -7.0, 10.5, -14)
        Tuple(1.0, -2.0, 3.0, -4) * 0.5 shouldBe Tuple(0.5, -1.0, 1.5, -2)
    }

    @Test
    fun `Dividing a tuple`() {
        Tuple(1.0, -2.0, 3.0, -4) / 2 shouldBe Tuple(0.5, -1.0, 1.5, -2)
    }

    @Test
    fun `Computing the magnitude of a vector`() {
        Tuple.vector(1.0, 0.0, 0.0).magnitude() shouldBe 1.0
        Tuple.vector(0.0, 1.0, 0.0).magnitude() shouldBe 1.0
        Tuple.vector(0.0, 0.0, 1.0).magnitude() shouldBe 1.0
        Tuple.vector(1.0, 2.0, 3.0).magnitude() shouldBe 3.7416573867739413 // √14
        Tuple.vector(-1.0, -2.0, -3.0).magnitude() shouldBe 3.7416573867739413 // √14
    }

    @Test
    fun `Normalizing a vector`() {
        Tuple.vector(4.0, 0.0, 0.0).normalize() shouldBe Tuple.vector(1.0, 0.0, 0.0)
        Tuple.vector(1.0, 2.0, 3.0).normalize() shouldBe Tuple.vector(0.2672612419124244, 0.5345224838248488, 0.8017837257372732) // 1/√14, 2/√14, 3/√14
    }

    @Test
    fun `The magnitude of a normalized vector`() {
        Tuple.vector(1.0, 2.0, 3.0).normalize().magnitude() shouldBe 1.0
    }

    @Test
    fun `The dot product of two tuples`() {
        Tuple.vector(1.0, 2.0, 3.0).dot(Tuple.vector(2.0, 3.0, 4.0)) shouldBe 20.0
    }

    @Test
    fun `The cross product of two vectors`() {
        Tuple.vector(1.0, 2.0, 3.0).cross(Tuple.vector(2.0, 3.0, 4.0)) shouldBe Tuple.vector(-1.0, 2.0, -1.0)
        Tuple.vector(2.0, 3.0, 4.0).cross(Tuple.vector(1.0, 2.0, 3.0)) shouldBe Tuple.vector(1.0, -2.0, 1.0)
    }

    @Test
    fun `Color are rgb tuples`() {
        val color = Tuple.color(0.9, 0.6, 0.75)

        color.x shouldBe 0.9
        color.y shouldBe 0.6
        color.z shouldBe 0.75
    }

    @Test
    fun `Adding colors`() {
        Tuple.color(0.9, 0.6, 0.75) + Tuple.color(0.7, 0.1, 0.25) shouldBe Tuple.color(1.6, 0.7, 1.0)
    }

    @Test
    fun `Subtracting colors`() {
        Tuple.color(0.9, 0.6, 0.75) - Tuple.color(0.7, 0.1, 0.25) shouldBe Tuple.color(0.20000000000000007, 0.5, 0.5)
    }

    @Test
    fun `Multiplying a color by a scalar`() {
        Tuple.color(0.2, 0.3, 0.4) * 2 shouldBe Tuple.color(0.4, 0.6, 0.8)
    }

    @Test
    fun `Multiplying colors`() {
        Tuple.color(1.0, 0.2, 0.4) * Tuple.color(0.9, 1.0, 0.1) shouldBe Tuple.color(0.9, 0.2, 0.04000000000000001)
    }

    @Test
    fun `Reflecting a vector approaching at 45`() {
        val vector = Tuple.vector(1.0, -1.0, 0.0)
        val normal = Tuple.vector(0.0, 1.0, 0.0)

        val reflectedVector = vector.reflect(normal)

        reflectedVector shouldBe Tuple.vector(1.0, 1.0, 0.0)
    }

    @Test
    fun `Reflecting a vector off a slanted surface`() {
        val vector = Tuple.vector(0.0, -1.0, 0.0)
        val normal = Tuple.vector(sqrt(2.0) / 2, sqrt(2.0) / 2, 0.0)

        val reflectedVector = vector.reflect(normal)

        reflectedVector shouldBe Tuple.vector(1.0, 0.0, 0.0)
    }

}