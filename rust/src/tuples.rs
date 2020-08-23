struct Tuple(f32, f32, f32, u8);

trait Tuples {
    fn new(x: f32, y: f32, z: f32, w: u8) -> Self;
    fn x(&self) -> f32;
    fn y(&self) -> f32;
    fn z(&self) -> f32;
    fn w(&self) -> u8;
}

impl Tuples for Tuple {

    fn new(x: f32, y: f32, z: f32, w: u8) -> Tuple {
        Tuple(x, y, z, w)
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
}

#[cfg(test)]
mod tests {
    use crate::tuples::{Tuple, Tuples};

    #[test]
    fn test() {
        let tuple: Tuple = Tuples::new(4.3, -4.2, 3.1, 1);

        assert_eq!(tuple.x(), 4.3);
        assert_eq!(tuple.y(), -4.2);
        assert_eq!(tuple.z(), 3.1);
        assert_eq!(tuple.w(), 1);

        // tuple is a point
        // tuple is not a vector
    }

}