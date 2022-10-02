package fpffp

import org.scalacheck.Prop._
import scala.annotation.experimental
import scala.language.experimental.fewerBraces
import Functor.given Functor[List]
import Functor.given Functor[Option]

@experimental
class FunctorSuite extends munit.ScalaCheckSuite:
  property("identity"):
    forAll((x: Int) => Option(x).map(identity) == Option(x))

  property("composition"):
    forAll((x: Int) =>
      Option(x)
        .map(((_: Int) * 2).compose(_ + 1)) == Option(x).map(_ + 1).map(_ * 2)
    )

  property("compose"):
    import Functor.given Functor[Compose[List, Option]]
    forAll((xs: List[Int]) =>
      val ys = xs.map(Option.apply)
      Functor[Compose[List, Option]].map(ys)(_ + 1) == xs.map(x =>
        Option(x + 1)
      )
    )
