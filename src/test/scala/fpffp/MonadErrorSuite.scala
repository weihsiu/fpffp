package fpffp

class MonadErrorSuite extends munit.FunSuite:
  def safeDivide[F[_]](x: Int, y: Int)(using
      AE: ApplicativeError[F, String]
  ): F[Int] =
    if y == 0 then "divide by zero".raiseError else (x / y).pure

  test("either") {}
