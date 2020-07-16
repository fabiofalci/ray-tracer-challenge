package raytracer.math

import kotlin.math.abs

infix fun Double.eq(other: Double) = abs(this - other) < 0.00000000000001
