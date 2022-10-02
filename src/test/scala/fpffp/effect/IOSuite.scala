package fpffp.effect

import fpffp.MonadError
import scala.annotation.experimental
import scala.language.experimental.fewerBraces
import IO.given

@experimental
class IOSuite extends munit.FunSuite:
  test("basics"):
    assertEquals(IO.delay(123).unsafePerformIO(), 123)
    assertEquals(IO.delay(1).map(_ + 1).unsafePerformIO(), 2)
    assertEquals(IO.delay(IO.delay("hello")).flatten.unsafePerformIO(), "hello")

  test("raiseError".fail):
    val raisingError =
      MonadError[IO, Throwable].raiseError(Exception("something went wrong"))
    raisingError.unsafePerformIO()

  test("handleError"):
    val raisingError: IO[String] =
      MonadError[IO, Throwable].raiseError(Exception("something went wrong"))
    assertEquals(
      raisingError.handleError(_ => IO.delay("all is good")).unsafePerformIO(),
      "all is good"
    )
