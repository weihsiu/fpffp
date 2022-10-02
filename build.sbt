name := "fpffp"
version := "0.0.1"
scalaVersion := "3.2.0"

scalacOptions ++= Seq(
)

libraryDependencies ++= Seq(
  "org.scalameta" %% "munit" % "1.0.0-M6" % Test,
  "org.scalameta" %% "munit-scalacheck" % "1.0.0-M6" % Test
)
