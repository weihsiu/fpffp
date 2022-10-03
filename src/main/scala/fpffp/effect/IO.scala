package fpffp.effect

import fpffp.MonadError
import scala.util.Try

case class IO[A](thunk: () => A):
  def unsafePerformIO(): A = thunk()

object IO:
  def delay[A](thunk: => A): IO[A] = IO(() => thunk)
  def defer[A](thunk: => IO[A]): IO[A] = thunk

  given MonadError[IO, Throwable] with
    def _pure[A](x: A): IO[A] = delay(x)
    def _raiseError[A](e: Throwable): IO[A] = delay(throw e)
    def _handleError[A](fa: IO[A])(f: Throwable => IO[A]): IO[A] =
      defer(Try(fa.unsafePerformIO()).fold(f, _pure))
    def _map[A, B](fa: IO[A])(f: A => B): IO[B] = delay(f(fa.unsafePerformIO()))
    def ap[A, B](ff: IO[A => B])(fa: IO[A]): IO[B] = ff.flatMap(f => fa.map(f))
    def _flatMap[A, B](fa: IO[A])(f: A => IO[B]): IO[B] =
      IO.defer(f(fa.unsafePerformIO()))
