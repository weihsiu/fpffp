package fpffp

import org.scalacheck.Prop._
import scala.annotation.experimental
import scala.language.experimental.fewerBraces
import Foldable.given Foldable[List]

@experimental
class FoldableSuite extends munit.ScalaCheckSuite:
  property("sum by foldLeft == sum by foldRight"):
    forAll((xs: List[Int]) => xs.foldLeft(0)(_ + _) == xs.foldRight(0)(_ + _))

  property("sum by foldLeft == sum by foldMap"):
    forAll((xs: List[Int]) => xs.foldLeft(0)(_ + _) == xs.foldMap(identity))
