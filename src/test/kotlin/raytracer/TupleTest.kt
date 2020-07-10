package raytracer

import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
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
    fun `point factory method`() {
        val tuple = Tuple.point(4.3, -4.2, 3.1)

        tuple.isPoint() shouldBe true
        tuple.isVector() shouldBe false
        tuple.w shouldBe 1
    }

    @Test
    fun `vector factory method`() {
        val tuple = Tuple.vector(4.3, -4.2, 3.1)

        tuple.isPoint() shouldBe false
        tuple.isVector() shouldBe true
        tuple.w shouldBe 0
    }

    @Test
    fun `vector equality`() {
        Tuple.vector(4.3, -4.2, 3.1) shouldBe Tuple.vector(4.3, -4.2, 3.1)
        Tuple.vector(4.4, -4.2, 3.1) shouldNotBe Tuple.vector(4.3, -4.2, 3.1)
        Tuple.vector(4.3, -4.3, 3.1) shouldNotBe Tuple.vector(4.3, -4.2, 3.1)
        Tuple.vector(4.3, -4.2, 3.2) shouldNotBe Tuple.vector(4.3, -4.2, 3.1)
    }

    @Test
    fun `point equality`() {
        Tuple.point(4.3, -4.2, 3.1) shouldBe Tuple.point(4.3, -4.2, 3.1)
        Tuple.point(4.4, -4.2, 3.1) shouldNotBe Tuple.point(4.3, -4.2, 3.1)
        Tuple.point(4.3, -4.3, 3.1) shouldNotBe Tuple.point(4.3, -4.2, 3.1)
        Tuple.point(4.3, -4.2, 3.2) shouldNotBe Tuple.point(4.3, -4.2, 3.1)

        Tuple.point(4.3, -4.2, 3.1) shouldNotBe Tuple.vector(4.3, -4.2, 3.1)
    }


}