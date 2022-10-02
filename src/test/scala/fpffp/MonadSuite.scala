package fpffp

import org.scalacheck.Prop._
import scala.annotation.experimental
import scala.language.experimental.fewerBraces
import Monad.given Monad[Option]

@experimental
class MonadSuite extends munit.ScalaCheckSuite:
  def inc[F[_]: Monad](x: Int): F[Int] = (x + 1).pure
  def double[F[_]: Monad](x: Int): F[Int] = (x * 2).pure

  property("associativity"):
    forAll((x: Int) =>
      Some(x).flatMap(inc).flatMap(double) == Some(x).flatMap(y =>
        inc(y).flatMap(double)
      )
    )

  property("left identity"):
    forAll((x: Int) => Monad[Option]._pure(x).flatMap(inc) == inc(x))

  property("right identity"):
    forAll((x: Int) => Some(x).flatMap(_.pure) == Some(x))
