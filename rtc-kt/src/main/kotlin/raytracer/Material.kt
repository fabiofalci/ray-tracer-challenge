package raytracer

data class Material(
    var color: Tuple = Colors.WHITE,
    var ambient: Double = 0.1,
    var diffuse: Double = 0.9,
    var specular: Double = 0.9,
    var shininess: Double = 200.0
) {
}