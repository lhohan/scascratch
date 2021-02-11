package thirdpartylogger

// scala.logging, sl4j, http, etc
class InfraLogger {
  def send(s: String): Unit = {
    println(s"Logger received: $s")
  }
}
