package fpffp

import scala.concurrent.{ExecutionContext, Future}

trait Monad[F[_]] extends Applicative[F]:
  def _flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]
  extension [A](fa: F[A]) def flatMap[B](f: A => F[B]): F[B] = _flatMap(fa)(f)
  extension [A](ffa: F[F[A]]) def flatten: F[A] = ffa.flatMap(identity)

object Monad:
  def apply[F[_]: Monad]: Monad[F] = summon[Monad[F]]

  given (using A: Applicative[Option]): Monad[Option] with
    export A.*
    def _flatMap[A, B](fa: Option[A])(f: A => Option[B]): Option[B] =
      fa.flatMap(f)

  given [E](using
      A: Applicative[[X] =>> Either[E, X]]
  ): Monad[[X] =>> Either[E, X]] with
    export A.*
    def _flatMap[A, B](fa: Either[E, A])(f: A => Either[E, B]): Either[E, B] =
      fa.flatMap(f)

  given [E](using
      A: Applicative[Future],
      EC: ExecutionContext
  ): Monad[Future] with
    export A.*
    def _flatMap[A, B](fa: Future[A])(f: A => Future[B]): Future[B] =
      fa.flatMap(f)
