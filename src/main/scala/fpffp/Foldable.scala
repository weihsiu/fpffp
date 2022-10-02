package fpffp

trait Foldable[F[_]]:
  def _foldLeft[A, B](fa: F[A])(b: B)(f: (B, A) => B): B
  def _foldRight[A, B](fa: F[A])(b: B)(f: (A, B) => B): B
  extension [A](fa: F[A])
    def foldLeft[B](b: B)(f: (B, A) => B): B = _foldLeft(fa)(b)(f)
    def foldRight[B](b: B)(f: (A, B) => B): B = _foldRight(fa)(b)(f)
    def foldMap[B](f: A => B)(using M: Monoid[B]): B =
      foldRight(M.mempty)((a, b) => f(a) <> b)

object Foldable:
  def apply[F[_]: Foldable]: Foldable[F] = summon[Foldable[F]]

  given Foldable[Option] with
    def _foldLeft[A, B](fa: Option[A])(b: B)(f: (B, A) => B): B =
      fa.fold(b)(a => f(b, a))
    def _foldRight[A, B](fa: Option[A])(b: B)(f: (A, B) => B): B =
      fa.fold(b)(a => f(a, b))

  given Foldable[List] with
    def _foldLeft[A, B](fa: List[A])(b: B)(f: (B, A) => B): B =
      fa.foldLeft(b)(f)
    def _foldRight[A, B](fa: List[A])(b: B)(f: (A, B) => B): B =
      fa.foldRight(b)(f)
