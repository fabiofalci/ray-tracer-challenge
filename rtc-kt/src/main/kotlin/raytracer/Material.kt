package raytracer

import kotlin.math.pow

data class Material(
    var color: Tuple = Colors.WHITE,
    var ambient: Double = 0.1,
    var diffuse: Double = 0.9,
    var specular: Double = 0.9,
    var shininess: Double = 200.0
) {

    companion object {
        fun lighting(material: Material, light: PointLight, point: Tuple, eyev: Tuple, normalv: Tuple): Tuple {
            val effectiveColor = material.color * light.intensity
            val lightv = (light.position - point).normalize()
            var ambient = effectiveColor * material.ambient

            var diffuse = Colors.BLACK
            var specular = Colors.BLACK
            val lightDotNormal = lightv.dot(normalv)
            if (lightDotNormal >= 0) {
                diffuse = effectiveColor * material.diffuse * lightDotNormal

                val reflectv = -lightv.reflect(normalv)
                val reflectDotEye = reflectv.dot(eyev)

                if (reflectDotEye > 0) {
                    val factor = reflectDotEye.pow(material.shininess)
                    specular = light.intensity * material.specular * factor
                }
            }

            return ambient + diffuse + specular
        }
    }


}