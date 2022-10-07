package fpffp.examples

import fpffp.*
import effect.{IO, given}
import scala.io.StdIn
import javax.script.SimpleBindings

def getLine(): IO[String] = IO.delay(StdIn.readLine())

def putLine(line: String): IO[Unit] = IO.delay(println(line))

@main
def ScalaIO() =
  val name: IO[String] =
    for
      _ <- putLine("Please enter your name:")
      name <- getLine()
      _ <- putLine(s"hey, $name, you have such beautiful name!")
    yield name
  println("press <enter> to perform IO")
  StdIn.readLine()
  println(name.unsafePerformIO())

  val ios = List(putLine("hello"), putLine("world"), putLine("how are you"))
  println(ios)
