use crate::tuples::Tuple;

struct Projectile {
    position: Tuple,
    velocity: Tuple
}

struct Environment {
    gravity: Tuple,
    wind: Tuple
}

impl Projectile {

    fn tick(&self, environemnt: &Environment) -> Projectile {
        return Projectile{ position: self.position() + self.velocity(), velocity: self.velocity() + environemnt.gravity() + environemnt.wind() }

    }
}