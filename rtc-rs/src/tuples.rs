use std::ops::{Add, Sub};

struct Tuple(f32, f32, f32, u8);

trait Tuples {
    fn new(x: f32, y: f32, z: f32, w: u8) -> Self;
    fn point(x: f32, y: f32, z: f32) -> Self;
    fn vector(x: f32, y: f32, z: f32) -> Self;

    fn x(&self) -> f32;
    fn y(&self) -> f32;
    fn z(&self) -> f32;
    fn w(&self) -> u8;

    fn is_point(&self) -> bool;
    fn is_vector(&self) -> bool;
}

impl Tuples for Tuple {
    fn new(x: f32, y: f32, z: f32, w: u8) -> Tuple {
        Tuple(x, y, z, w)
    }

    fn point(x: f32, y: f32, z: f32) -> Tuple {
        Tuple(x, y, z, 1)
    }

    fn vector(x: f32, y: f32, z: f32) -> Tuple {
        Tuple(x, y, z, 0)
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

    fn w(&self) -> u8 {
        self.3
    }

    fn is_point(&self) -> bool {
        self.w() == 1
    }

    fn is_vector(&self) -> bool {
        self.w() == 0
    }
}

impl Add for Tuple {
    type Output = Tuple;

    fn add(self, rhs: Self) -> Self::Output {
        Tuples::new(self.x() + rhs.x(), self.y() + rhs.y(), self.z() + rhs.z(), self.w() + rhs.w())
    }
}

impl Sub for Tuple {
    type Output = Tuple;

    fn sub(self, rhs: Self) -> Self::Output {
        Tuples::new(self.x() - rhs.x(), self.y() - rhs.y(), self.z() - rhs.z(), self.w() - rhs.w())
    }
}

#[cfg(test)]
mod tests {
    use crate::tuples::{Tuple, Tuples};

    #[test]
    fn tuple_with_1_is_a_point() {
        let tuple: Tuple = Tuples::new(4.3, -4.2, 3.1, 1);

        assert_eq!(tuple.x(), 4.3);
        assert_eq!(tuple.y(), -4.2);
        assert_eq!(tuple.z(), 3.1);
        assert_eq!(tuple.w(), 1);
        assert_eq!(tuple.is_point(), true);
        assert_eq!(tuple.is_vector(), false);
    }

    #[test]
    fn tuple_with_0_is_a_vector() {
        let tuple: Tuple = Tuples::new(4.3, -4.2, 3.1, 0);

        assert_eq!(tuple.x(), 4.3);
        assert_eq!(tuple.y(), -4.2);
        assert_eq!(tuple.z(), 3.1);
        assert_eq!(tuple.w(), 0);
        assert_eq!(tuple.is_point(), false);
        assert_eq!(tuple.is_vector(), true);
    }

    #[test]
    fn create_point() {
        let tuple: Tuple = Tuples::point(4.3, -4.2, 3.1);

        assert_eq!(tuple.w(), 1);
        assert_eq!(tuple.is_point(), true);
        assert_eq!(tuple.is_vector(), false);
    }

    #[test]
    fn create_vector() {
        let tuple: Tuple = Tuples::vector(4.3, -4.2, 3.1);

        assert_eq!(tuple.w(), 0);
        assert_eq!(tuple.is_point(), false);
        assert_eq!(tuple.is_vector(), true);
    }

    #[test]
    fn adding_two_tuples() {
        let a1: Tuple = Tuples::new(3.0, -2.0, 5.0, 1);
        let a2: Tuple = Tuples::new(-2.0, 3.0, 1.0, 0);

        let a3 = a1 + a2;

        assert_eq!(a3.x(), 1.0);
        assert_eq!(a3.y(), 1.0);
        assert_eq!(a3.z(), 6.0);
        assert_eq!(a3.w(), 1);
    }

    #[test]
    fn subtracting_two_points() {
        let p1: Tuple = Tuples::point(3.0, 2.0, 1.0);
        let p2: Tuple = Tuples::point(5.0, 6.0, 7.0);

        let vector = p1 - p2;

        assert_eq!(vector.x(), -2.0);
        assert_eq!(vector.y(), -4.0);
        assert_eq!(vector.z(), -6.0);
        assert_eq!(vector.w(), 0);
    }

    #[test]
    fn subtracting_vector_from_points() {
        let p1: Tuple = Tuples::point(3.0, 2.0, 1.0);
        let v1: Tuple = Tuples::vector(5.0, 6.0, 7.0);

        let point = p1 - v1;

        assert_eq!(point.x(), -2.0);
        assert_eq!(point.y(), -4.0);
        assert_eq!(point.z(), -6.0);
        assert_eq!(point.w(), 1);
    }

    #[test]
    fn subtracting_two_vectors() {
        let v1: Tuple = Tuples::vector(3.0, 2.0, 1.0);
        let v2: Tuple = Tuples::vector(5.0, 6.0, 7.0);

        let vector = v1 - v2;

        assert_eq!(vector.x(), -2.0);
        assert_eq!(vector.y(), -4.0);
        assert_eq!(vector.z(), -6.0);
        assert_eq!(vector.w(), 0);
    }

}