package fpffp

import scala.concurrent.{ExecutionContext, Future}

trait ApplicativeError[F[_], E] extends Applicative[F]:
  def _raiseError[A](e: E): F[A]
  def _handleError[A](fa: F[A])(f: E => F[A]): F[A]
  extension [A](e: E) def raiseError: F[A] = _raiseError(e)
  extension [A](fa: F[A])
    def handleError(f: E => F[A]): F[A] = _handleError(fa)(f)

object ApplicativeError:
  def apply[F[_], E](using AE: ApplicativeError[F, E]): ApplicativeError[F, E] =
    AE

  given [E](using
      A: Applicative[[X] =>> Either[E, X]]
  ): ApplicativeError[[X] =>> Either[E, X], E] with
    export A.*
    def _raiseError[A](e: E): Either[E, A] = Left(e)
    def _handleError[A](fa: Either[E, A])(f: E => Either[E, A]): Either[E, A] =
      fa.fold(f, _.pure)

  given [E](using
      A: Applicative[Future],
      EC: ExecutionContext
  ): ApplicativeError[Future, Throwable] with
    export A.*
    def _raiseError[A](e: Throwable): Future[A] = Future.failed(e)
    def _handleError[A](fa: Future[A])(f: Throwable => Future[A]): Future[A] =
      fa.recoverWith(PartialFunction.fromFunction(f))
