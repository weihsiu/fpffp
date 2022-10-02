package fpffp

import scala.concurrent.{Future, ExecutionContext}

trait Applicative[F[_]] extends Functor[F]:
  def _pure[A](x: A): F[A]
  def ap[A, B](ff: F[A => B])(fa: F[A]): F[B]
  extension [A, B](ff: F[A => B]) def <*>(fa: F[A]): F[B] = ap(ff)(fa)
  extension [A](x: A) def pure: F[A] = _pure(x)
  extension [A](fa: F[A])
    def product[B](fb: F[B]): F[(A, B)] =
      fa.map(a => (b: B) => (a, b)) <*> fb
    def map2[B, C](fb: F[B])(f: (A, B) => C): F[C] =
      fa.product(fb).map(f.tupled)
    def <*[B](fb: F[B]): F[A] = fa.map2(fb)((a, _) => a)
    def *>[B](fb: F[B]): F[B] = fa.map2(fb)((_, b) => b)

object Applicative:
  def apply[F[_]: Applicative]: Applicative[F] = summon[Applicative[F]]

  given (using F: Functor[Option]): Applicative[Option] with
    export F.*
    def _pure[A](x: A): Option[A] = Some(x)
    def ap[A, B](ff: Option[A => B])(fa: Option[A]): Option[B] =
      ff.flatMap(fa.map)

  given [E](using
      F: Functor[[X] =>> Either[E, X]]
  ): Applicative[[X] =>> Either[E, X]] with
    export F.*
    def _pure[A](x: A): Either[E, A] = Right(x)
    def ap[A, B](ff: Either[E, A => B])(fa: Either[E, A]): Either[E, B] =
      ff.flatMap(fa.map)

  given (using F: Functor[Future], EC: ExecutionContext): Applicative[Future]
    with
    export F.*
    def _pure[A](x: A): Future[A] = Future.successful(x)
    def ap[A, B](ff: Future[A => B])(fa: Future[A]): Future[B] =
      ff.flatMap(fa.map)

  given [F[_], G[_]](using
      AF: Applicative[F],
      AG: Applicative[G],
      F: Functor[Compose[F, G]]
  ): Applicative[Compose[F, G]] with
    export F.*
    def _pure[A](x: A): Compose[F, G][A] = AF._pure(AG._pure(x))
    def ap[A, B](fgf: Compose[F, G][A => B])(
        fga: Compose[F, G][A]
    ): Compose[F, G][B] =
      AF.ap(AF.map(fgf)(gf => (ga: G[A]) => AG.ap(gf)(ga)))(fga)
