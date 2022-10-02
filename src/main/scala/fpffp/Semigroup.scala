package fpffp

import scala.math.Numeric

trait Semigroup[A]:
  def combine(x: A, y: A): A
  extension (x: A) def <>(y: A): A = combine(x, y)

object Semigroup:
  def apply[A: Semigroup]: Semigroup[A] = summon[Semigroup[A]]

  given [A](using N: Numeric[A]): Semigroup[A] with
    def combine(x: A, y: A): A = N.plus(x, y)
