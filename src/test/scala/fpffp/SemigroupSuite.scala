package fpffp

import org.scalacheck.Prop._
import scala.annotation.experimental
import scala.language.experimental.fewerBraces
import Semigroup.given Semigroup[Int]

@experimental
class SemigroupSuite extends munit.ScalaCheckSuite:
  property("associativity"):
    forAll { (x: Int, y: Int, z: Int) =>
      x <> (y <> z) == (x <> y) <> z
    }
