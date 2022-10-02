package fpffp

import org.scalacheck.Prop._
import scala.annotation.experimental
import scala.language.experimental.fewerBraces
import Applicative.given Applicative[Option]

@experimental
class ApplicativeSuite extends munit.ScalaCheckSuite:
  property("associativity"):
    forAll((x: Int, y: Int, z: Int) =>
      Some(x).product(Some(y)).product(Some(z)) == Some(x)
        .product(
          Some(y).product(Some(z))
        )
        .map:
          case (x, (y, z)) => ((x, y), z)
    )

  property("left identity"):
    forAll((x: Int) =>
      Applicative[Option]._pure(()).product(Some(x)).map(_._2) == Some(x)
    )

  property("right identity"):
    forAll((x: Int) => Some(x).product(().pure).map(_._1) == Some(x))

  test("<@> and <*>"):
    def addAll(x: Int, y: Int, z: Int): Int = x + y + z
    assertEquals(
      addAll.curried <@> Option(1) <*> Option(2) <*> Option(3),
      Option(6)
    )