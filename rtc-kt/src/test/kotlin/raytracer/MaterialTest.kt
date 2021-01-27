package raytracer

import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test
import kotlin.math.sqrt

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

    @Test
    fun `Lighting with the eye between the light and the surface`() {
        val material = Material()
        val position = Tuple.point(0.0, 0.0, 0.0)

        val eyev = Tuple.vector(0.0, 0.0, -1.0)
        val normalv = Tuple.vector(0.0, 0.0, -1.0)
        val light = PointLight(Tuple.point(0.0, 0.0, -10.0), Colors.WHITE)

        val result = Material.lighting(material, light, position, eyev, normalv)

        result shouldBe Tuple.color(1.9, 1.9, 1.9)
    }

    @Test
    fun `Lighting with the eye between light and surface, eye offset 45`() {
        val material = Material()
        val position = Tuple.point(0.0, 0.0, 0.0)

        val eyev = Tuple.vector(0.0, sqrt(2.0) / 2.0, -sqrt(2.0) / 2.0)
        val normalv = Tuple.vector(0.0, 0.0, -1.0)
        val light = PointLight(Tuple.point(0.0, 0.0, -10.0), Colors.WHITE)

        val result = Material.lighting(material, light, position, eyev, normalv)

        result shouldBe Tuple.color(1.0, 1.0, 1.0)
    }

    @Test
    fun `Lighting with the eye opposite surface, light offset 45`() {
        val material = Material()
        val position = Tuple.point(0.0, 0.0, 0.0)

        val eyev = Tuple.vector(0.0, 0.0, -1.0)
        val normalv = Tuple.vector(0.0, 0.0, -1.0)
        val light = PointLight(Tuple.point(0.0, 10.0, -10.0), Colors.WHITE)

        val result = Material.lighting(material, light, position, eyev, normalv)

        result shouldBe Tuple.color(0.7363961030678927, 0.7363961030678927, 0.7363961030678927)
    }

    @Test
    fun `Lighting with the eye in the path of the reflection vector`() {
        val material = Material()
        val position = Tuple.point(0.0, 0.0, 0.0)

        val eyev = Tuple.vector(0.0, -sqrt(2.0) / 2.0, -sqrt(2.0) / 2.0)
        val normalv = Tuple.vector(0.0, 0.0, -1.0)
        val light = PointLight(Tuple.point(0.0, 10.0, -10.0), Colors.WHITE)

        val result = Material.lighting(material, light, position, eyev, normalv)

        result shouldBe Tuple.color(1.6363961030678928, 1.6363961030678928, 1.6363961030678928)
    }

    @Test
    fun `Lighting with the light behind the surface`() {
        val material = Material()
        val position = Tuple.point(0.0, 0.0, 0.0)

        val eyev = Tuple.vector(0.0, 0.0, -1.0)
        val normalv = Tuple.vector(0.0, 0.0, -1.0)
        val light = PointLight(Tuple.point(0.0, 0.0, 10.0), Colors.WHITE)

        val result = Material.lighting(material, light, position, eyev, normalv)

        result shouldBe Tuple.color(0.1, 0.1, 0.1)
    }

}