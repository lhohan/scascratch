package domain

import domain.StructuredLogger.Message
import domain.User.{DomainError, EmptyName, InvalidName}
import upickle.default._
import upickle.default.{macroRW, ReadWriter => RW}

trait Logger {
  def invalidPasswordAttempt(): Unit
  def error(error: DomainError): Unit
}

object Logger {

  val logger = new StructuredLogger(new RawLogger().send)

  def create(): Logger = new Logger {

    override def invalidPasswordAttempt(): Unit = ???

    override def error(error: DomainError): Unit = {
      error match {
        case EmptyName => {
          // log as error
          logger.log(Message("error", "Attempt with empty string", "userservice", ""))
        }
        case InvalidName(illegalName) => {
          // log to audit log, wrong inserted name may be sensitive
          logger.log(Message("audit", s"User creation attempt with name $illegalName", "userservice", illegalName.reverse))
        }
      }
    }
  }
}

object StructuredLogger {
  case class Message(category: String, msg: String, service: String, hash: String)
  object Message{
    implicit val rw: RW[Message] = macroRW
  }
}

class StructuredLogger(logger: String => Unit){
  def log(message: Message): Unit = {
    logger(write(message))
  }
}

// scala.logging, sl4j, http, etc
class RawLogger {
  def send(s: String): Unit = {
    println(s"Logger received: $s")
  }
}


