import mill._
import mill.scalalib._
import mill.scalalib.scalafmt._

object domainlogger extends ScalaModule with ScalafmtModule {

  def scalaVersion = "2.13.4"

  def ivyDeps = Agg(ivy"com.lihaoyi::upickle:0.9.5")


  object test extends Tests {
    def ivyDeps = Agg(ivy"org.scalatest::scalatest:3.2.2")

    def testFrameworks = Seq("org.scalatest.tools.Framework")
  }

}
