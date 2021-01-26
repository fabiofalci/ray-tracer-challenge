package raytracer

import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test


internal class PointLightTest {

    @Test
    fun `A point light has a position and intensity`() {
        val intensity = Colors.WHITE
        val position = Tuple.point(0.0, 0.0, 0.0)

        val light = PointLight(position, intensity)

        light.position shouldBe position
        light.intensity shouldBe intensity
    }

}