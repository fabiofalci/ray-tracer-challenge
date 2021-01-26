package raytracer

import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

internal class MaterialTest {

    @Test
    fun `The default material`() {
        val material = Material()
        material.color shouldBe Colors.WHITE
        material.ambient shouldBe 0.1
        material.diffuse shouldBe 0.9
        material.specular shouldBe 0.9
        material.shininess shouldBe 200.0
    }

}