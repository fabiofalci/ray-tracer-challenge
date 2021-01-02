use std::ops::{Add, Sub, Neg, Mul, Div};

#[derive(PartialEq, PartialOrd, Debug)]
struct Tuple(f32, f32, f32, f32);

#[allow(dead_code)]
impl Tuple {
    fn new(x: f32, y: f32, z: f32, w: f32) -> Tuple {
        Tuple(x, y, z, w)
    }

    fn point(x: f32, y: f32, z: f32) -> Tuple {
        Tuple(x, y, z, 1.0)
    }

    fn vector(x: f32, y: f32, z: f32) -> Tuple {
        Tuple(x, y, z, 0.0)
    }

    fn x(&self) -> f32 {
        self.0
    }

    fn y(&self) -> f32 {
        self.1
    }

    fn z(&self) -> f32 {
        self.2
    }

    fn w(&self) -> f32 {
        self.3
    }

    fn is_point(&self) -> bool {
        self.w() == 1.0
    }

    fn is_vector(&self) -> bool {
        self.w() == 0.0
    }

    fn magnitude(&self) -> f32 {
        (self.x().powi(2) + self.y().powi(2) + self.z().powi(2) + self.w().powi(2)).sqrt()
    }

    fn normalize(&self) -> Tuple {
        Tuple::vector(
            self.x() / self.magnitude(),
            self.y() / self.magnitude(),
            self.z() / self.magnitude(),
        )
    }
}

impl Add for Tuple {
    type Output = Tuple;

    fn add(self, rhs: Self) -> Self::Output {
        Tuple::new(self.x() + rhs.x(), self.y() + rhs.y(), self.z() + rhs.z(), self.w() + rhs.w())
    }
}

impl Sub for Tuple {
    type Output = Tuple;

    fn sub(self, rhs: Self) -> Self::Output {
        Tuple::new(self.x() - rhs.x(), self.y() - rhs.y(), self.z() - rhs.z(), self.w() - rhs.w())
    }
}

impl Neg for Tuple {
    type Output = Tuple;

    fn neg(self) -> Self::Output {
        Tuple::new(-self.x(), -self.y(), -self.z(), -self.w())
    }
}

impl Mul<f32> for Tuple {
    type Output = Tuple;

    fn mul(self, rhs: f32) -> Self::Output {
        Tuple::new(self.x() * rhs, self.y() * rhs, self.z() * rhs, self.w() * rhs)
    }
}

impl Div<f32> for Tuple {
    type Output = Tuple;

    fn div(self, rhs: f32) -> Self::Output {
        Tuple::new(self.x() / rhs, self.y() / rhs, self.z() / rhs, self.w() / rhs)
    }
}

#[cfg(test)]
mod tests {
    use crate::tuples::{Tuple};

    #[test]
    fn tuple_with_1_is_a_point() {
        let tuple = Tuple::new(4.3, -4.2, 3.1, 1.0);

        assert_eq!(tuple.x(), 4.3);
        assert_eq!(tuple.y(), -4.2);
        assert_eq!(tuple.z(), 3.1);
        assert_eq!(tuple.w(), 1.0);
        assert_eq!(tuple.is_point(), true);
        assert_eq!(tuple.is_vector(), false);
    }

    #[test]
    fn tuple_with_0_is_a_vector() {
        let tuple = Tuple::new(4.3, -4.2, 3.1, 0.0);

        assert_eq!(tuple.x(), 4.3);
        assert_eq!(tuple.y(), -4.2);
        assert_eq!(tuple.z(), 3.1);
        assert_eq!(tuple.w(), 0.0);
        assert_eq!(tuple.is_point(), false);
        assert_eq!(tuple.is_vector(), true);
    }

    #[test]
    fn create_point() {
        let tuple = Tuple::point(4.3, -4.2, 3.1);

        assert_eq!(tuple.w(), 1.0);
        assert_eq!(tuple.is_point(), true);
        assert_eq!(tuple.is_vector(), false);
    }

    #[test]
    fn create_vector() {
        let tuple = Tuple::vector(4.3, -4.2, 3.1);

        assert_eq!(tuple.w(), 0.0);
        assert_eq!(tuple.is_point(), false);
        assert_eq!(tuple.is_vector(), true);
    }

    #[test]
    fn adding_two_tuples() {
        let a1 = Tuple::new(3.0, -2.0, 5.0, 1.0);
        let a2 = Tuple::new(-2.0, 3.0, 1.0, 0.0);

        let a3 = a1 + a2;

        assert_eq!(a3.x(), 1.0);
        assert_eq!(a3.y(), 1.0);
        assert_eq!(a3.z(), 6.0);
        assert_eq!(a3.w(), 1.0);
    }

    #[test]
    fn subtracting_two_points() {
        let p1 = Tuple::point(3.0, 2.0, 1.0);
        let p2 = Tuple::point(5.0, 6.0, 7.0);

        let vector = p1 - p2;

        assert_eq!(vector.x(), -2.0);
        assert_eq!(vector.y(), -4.0);
        assert_eq!(vector.z(), -6.0);
        assert_eq!(vector.w(), 0.0);
    }

    #[test]
    fn subtracting_vector_from_points() {
        let p1 = Tuple::point(3.0, 2.0, 1.0);
        let v1 = Tuple::vector(5.0, 6.0, 7.0);

        let point = p1 - v1;

        assert_eq!(point.x(), -2.0);
        assert_eq!(point.y(), -4.0);
        assert_eq!(point.z(), -6.0);
        assert_eq!(point.w(), 1.0);
    }

    #[test]
    fn subtracting_two_vectors() {
        let v1 = Tuple::vector(3.0, 2.0, 1.0);
        let v2 = Tuple::vector(5.0, 6.0, 7.0);

        let vector = v1 - v2;

        assert_eq!(vector.x(), -2.0);
        assert_eq!(vector.y(), -4.0);
        assert_eq!(vector.z(), -6.0);
        assert_eq!(vector.w(), 0.0);
    }

    #[test]
    fn subtracting_vector_from_vector_zero() {
        let zero = Tuple::vector(0.0, 0.0, 0.0);
        let vector = Tuple::vector(1.0, -2.0, 3.0);

        let result = zero - vector;

        assert_eq!(result.x(), -1.0);
        assert_eq!(result.y(), 2.0);
        assert_eq!(result.z(), -3.0);
        assert_eq!(result.w(), 0.0);
    }

    #[test]
    fn negating_a_tuple() {
        let tuple = Tuple::new(1.0, -2.0, 3.0, -4.0);

        let result = -tuple;

        assert_eq!(result.x(), -1.0);
        assert_eq!(result.y(), 2.0);
        assert_eq!(result.z(), -3.0);
        assert_eq!(result.w(), 4.0);
    }

    #[test]
    fn multiply_tuple_by_scalar() {
        let tuple = Tuple::new(1.0, -2.0, 3.0, -4.0);

        let result = tuple * 3.5;

        assert_eq!(result.x(), 3.5);
        assert_eq!(result.y(), -7.0);
        assert_eq!(result.z(), 10.5);
        assert_eq!(result.w(), -14.0);
    }

    #[test]
    fn multiply_tuple_by_fraction() {
        let tuple = Tuple::new(1.0, -2.0, 3.0, -4.0);

        let result = tuple * 0.5;

        assert_eq!(result.x(), 0.5);
        assert_eq!(result.y(), -1.0);
        assert_eq!(result.z(), 1.5);
        assert_eq!(result.w(), -2.0);
    }

    #[test]
    fn diving_tuple_by_scalar() {
        let tuple = Tuple::new(1.0, -2.0, 3.0, -4.0);

        let result = tuple / 2.0;

        assert_eq!(result.x(), 0.5);
        assert_eq!(result.y(), -1.0);
        assert_eq!(result.z(), 1.5);
        assert_eq!(result.w(), -2.0);
    }

    #[test]
    fn computing_vectors_magnitude() {
        assert_eq!(Tuple::vector(1.0, 0.0, 0.0).magnitude(), 1.0);
        assert_eq!(Tuple::vector(0.0, 1.0, 0.0).magnitude(), 1.0);
        assert_eq!(Tuple::vector(0.0, 0.0, 1.0).magnitude(), 1.0);
        assert_eq!(Tuple::vector(1.0, 2.0, 3.0).magnitude(), 14.0_f32.sqrt());
        assert_eq!(Tuple::vector(-1.0, -2.0, -3.0).magnitude(), 14.0_f32.sqrt());
    }

    #[test]
    fn normalizing_vectors() {
        assert_eq!(Tuple::vector(4.0, 0.0, 0.0).normalize(), Tuple::vector(1.0, 0.0, 0.0));
        assert_eq!(Tuple::vector(1.0, 2.0, 3.0).normalize(), Tuple::vector(1.0 / 14.0_f32.sqrt(), 2.0 / 14.0_f32.sqrt(), 3.0 / 14.0_f32.sqrt()));
    }

    #[test]
    fn magnitude_of_a_normalized_vectors() {
        assert_eq!(Tuple::vector(4.0, 0.0, 0.0).normalize().magnitude(), 1.0);
    }

}