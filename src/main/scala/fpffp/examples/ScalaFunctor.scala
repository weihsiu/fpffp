package fpffp.examples

import fpffp.*
import Functor.given Functor[List]
import Functor.given Functor[Option]
import Functor.given Functor[Compose[List, Option]]

@main
def ScalaFunctor() =
  val ss = List(1, 2, 3, 4, 5).map("a".repeat)
  val os1 = Option(123).map(_.toString)
  val os2 = None.map(_.toString)

  val ns = List(-6, 4, 1, -2, 3, -5).map(n => if n < 0 then None else Some(n))
  println(Functor[Compose[List, Option]].map(ns)("a".repeat))
