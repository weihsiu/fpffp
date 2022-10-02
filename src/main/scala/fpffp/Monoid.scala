package fpffp

trait Monoid[A] extends Semigroup[A]:
  def mempty: A

object Monoid:
  def apply[A: Monoid]: Monoid[A] = summon[Monoid[A]]

  given [A](using N: Numeric[A], S: Semigroup[A]): Monoid[A] with
    export S.*
    def mempty: A = N.zero
