package fpffp

import org.scalacheck.Prop._
import scala.annotation.experimental
import scala.language.experimental.fewerBraces
import Traverse.given Traverse[List]

@experimental
class TraverseSuite extends munit.ScalaCheckSuite:
  property("traverse Option"):
    forAll((xs: List[Int]) => xs.traverse(Option.apply) == Option(xs))

  property("sequence Option"):
    forAll((xs: List[Int]) => xs.map(Option.apply).sequence == Option(xs))
