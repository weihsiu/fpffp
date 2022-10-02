package fpffp

import scala.annotation.experimental
import scala.language.experimental.fewerBraces
import ApplicativeError.given ApplicativeError[
  [X] =>> Either[String, X],
  String
]

@experimental
class ApplicativeErrorSuite extends munit.FunSuite:
  def safeDivide[F[_]](x: Int, y: Int)(using
      AE: ApplicativeError[F, String]
  ): F[Int] =
    if y == 0 then "divide by zero".raiseError else (x / y).pure

  test("either"):
    val r1: Either[String, Int] = safeDivide(1, 0)
    assertEquals(r1, Left("divide by zero"))
    val r2: Either[String, Int] = safeDivide(2, 1)
    assertEquals(r2, Right(2))
    val r3: Either[String, Int] = safeDivide(1, 0).handleError(_ => 0.pure)
    assertEquals(r3, Right(0))
