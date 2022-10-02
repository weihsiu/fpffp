package fpffp

class CoherencySuite extends munit.FunSuite:
  def syntax[F[_], A](fa: F[A])(using T: Traverse[F], M: Monad[F]): F[Unit] =
    given Monad[F] = M
    fa.map(_ => Option(())).sequence.get

  test("adelbert's problem") {}
