package fpffp

trait Traverse[F[_]] extends Functor[F], Foldable[F]:
  def _traverse[G[_], A, B](fa: F[A])(f: A => G[B])(using
      Applicative[G]
  ): G[F[B]]
  extension [A](fa: F[A])
    def traverse[G[_], B](f: A => G[B])(using Applicative[G]): G[F[B]] =
      _traverse(fa)(f)
  extension [G[_], A](fga: F[G[A]])
    def sequence(using Applicative[G]): G[F[A]] = fga.traverse(identity)

object Traverse:
  def apply[F[_]: Traverse]: Traverse[F] = summon[Traverse[F]]

  given (using FU: Functor[List], FO: Foldable[List]): Traverse[List] with
    export FU.*
    export FO.*
    def _traverse[G[_], A, B](fa: List[A])(f: A => G[B])(using
        A: Applicative[G]
    ): G[List[B]] =
      fa.foldRight(Nil.pure)((x, a) => f(x).map2(a)(_ :: _))
