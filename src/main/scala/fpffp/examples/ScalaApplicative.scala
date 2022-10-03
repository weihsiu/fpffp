package fpffp.examples

import fpffp.*
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.concurrent.duration.*

def optionApplicative() =
  import Applicative.given Applicative[Option]
  def repeatString(str: String, times: Int): String = str.repeat(times)
  val strO: Option[String] = Some("hello ")
  val intO: Option[Int] = Some(5)
  // like calling def repeatString(Option[String], Option[Int]):  Option[String]
  println(repeatString.curried <@> strO <*> intO)
  println(repeatString.curried <@> Option("world ") <*> None)

def futureApplicative() =
  import Applicative.given Applicative[Future]
  case class User(name: String, country: String)
  val nameF: Future[String] =
    Future.successful("Walter") // or get it via some network call
  val addressF: Future[String] =
    Future.successful("Taiwan") // or get it via some network call
  val userF: Future[User] = User.apply.curried <@> nameF <*> addressF
  println(Await.result(userF, 1.second))

def futureOptionApplicative() =
  import Applicative.given Applicative[Option]
  import Applicative.given Applicative[Future]
  import Applicative.given Applicative[Compose[Future, Option]]
  def accountBalanceInUSD(
      usd2Ntd: BigDecimal,
      balanceInNtd: BigDecimal
  ): BigDecimal = balanceInNtd / usd2Ntd
  val usd2NtdFO: Future[Option[BigDecimal]] = Future.successful(Some(30.0))
  val balanceNtdFO: Future[Option[BigDecimal]] =
    Future.successful(Some(10000.0))
  println(
    Await.result(
      Applicative[Compose[Future, Option]].map2(usd2NtdFO)(balanceNtdFO)(
        accountBalanceInUSD
      ),
      1.second
    )
  )

@main
def ScalaApplicative() =
  optionApplicative()
  futureApplicative()
  futureOptionApplicative()
