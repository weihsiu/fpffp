package fpffp.examples

import fpffp.*
import Applicative.given Applicative[Option]

@main
def ScalaTraverse() =
  import Traverse.given Traverse[List]
  val ons1: List[Option[Int]] = List(Some(1), Some(2), Some(3))
  println(ons1.sequence)
  val ons2: List[Option[Int]] = List(Some(1), Some(2), None)
  println(ons2.sequence)
