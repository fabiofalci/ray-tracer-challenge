package raytracer

import io.kotlintest.matchers.string.shouldStartWith
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.shouldThrow
import org.junit.jupiter.api.Test

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
        -Tuple.point(3.0, -2.0, 1.0) shouldBe Tuple.point(-3.0, 2.0, -1.0)
        -Tuple.vector(3.0, -2.0, 1.0) shouldBe Tuple.vector(-3.0, 2.0, -1.0)
    }

}