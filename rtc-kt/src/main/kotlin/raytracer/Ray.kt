package raytracer

class Ray(val origin: Tuple, val direction: Tuple) {

    fun position(time: Double): Tuple {
        return origin + direction * time
    }

    fun transform(transformation: Array<Array<Double>>): Ray {
        return Ray(Matrices.multiply(transformation, origin), Matrices.multiply(transformation, direction))
    }
}