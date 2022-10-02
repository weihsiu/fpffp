package fpffp.examples

import fpffp.*
import Monad.given Monad[[X] =>> Either[String, X]]
import scala.util.Random

def getBirthYear(name: String): Either[String, Int] =
  if name == "Walter" then Left("no such user")
  else Right(Random.nextInt(100) + 1920)

def getGeneration(birthYear: Int): Either[String, String] =
  if Range(1928, 1946).contains(birthYear) then Right("Silent")
  else if Range(1946, 1965).contains(birthYear) then Right("Boomers")
  else if Range(1965, 1981).contains(birthYear) then Right("Gen X")
  else if Range(1081, 1997).contains(birthYear) then Right("Millennials")
  else if Range(1997, 2012).contains(birthYear) then Right("Gen Z")
  else Left(s"Year $birthYear is not categorized")

def getFamousPersonByGen(gen: String): Either[String, String] =
  gen match
    case "Silent"      => Right("John Lennon")
    case "Boomers"     => Right("Donald Trump")
    case "Gen X"       => Right("Kobe Bryant")
    case "Millennials" => Right("Cristiano Ronaldo")
    case "GenZ"        => Left("No one")

@main
def ScalaMonad() =
  val famousPerson1: Either[String, String] =
    getBirthYear("Chang").flatMap(birthYear =>
      getGeneration(birthYear).flatMap(gen => getFamousPersonByGen(gen))
    )
  println(famousPerson1)

  val famousPerson2 = for
    birthYear <- getBirthYear("Walter")
    gen <- getGeneration(birthYear)
    person <- getFamousPersonByGen(gen)
  yield person
  println(famousPerson2)
