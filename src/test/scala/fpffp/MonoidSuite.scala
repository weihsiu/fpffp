package fpffp

import org.scalacheck.Prop._
import scala.annotation.experimental
import scala.language.experimental.fewerBraces
import Monoid.given Monoid[Int]

@experimental
class MonoidSuite extends munit.ScalaCheckSuite:
  property("associativity"):
    forAll((x: Int, y: Int, z: Int) => x <> (y <> z) == (x <> y) <> z)

  property("left identity"):
    forAll((x: Int) => Monoid[Int].mempty <> x == x)

  property("right identity"):
    forAll((x: Int) => x <> Monoid[Int].mempty == x)
