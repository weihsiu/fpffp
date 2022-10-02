package fpffp

import scala.concurrent.{ExecutionContext, Future}

trait Functor[F[_]]:
  def _map[A, B](fa: F[A])(f: A => B): F[B]
  extension [A](fa: F[A]) def map[B](f: A => B): F[B] = _map(fa)(f)
  extension [A, B](f: A => B) def <@>(fa: F[A]): F[B] = _map(fa)(f)

object Functor:
  def apply[F[_]: Functor]: Functor[F] = summon[Functor[F]]

  given Functor[Option] with
    def _map[A, B](fa: Option[A])(f: A => B): Option[B] = fa.map(f)

  given [E]: Functor[[X] =>> Either[E, X]] with
    def _map[A, B](fa: Either[E, A])(f: A => B): Either[E, B] = fa.map(f)

  given Functor[List] with
    def _map[A, B](fa: List[A])(f: A => B): List[B] = fa.map(f)

  given (using ExecutionContext): Functor[Future] with
    def _map[A, B](fa: Future[A])(f: A => B): Future[B] = fa.map(f)

  given [F[_], G[_]](using F: Functor[F], G: Functor[G]): Functor[Compose[F, G]]
    with
    def _map[A, B](fga: Compose[F, G][A])(f: A => B): Compose[F, G][B] =
      F._map(fga)(ga => G._map(ga)(f))
