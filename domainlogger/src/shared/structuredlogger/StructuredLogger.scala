package shared.structuredlogger

import shared.structuredlogger.StructuredLogger.Message
import thirdpartylogger.InfraLogger
import upickle.default.{macroRW, write, ReadWriter => RW}

object StructuredLogger {

  def default(): StructuredLogger = {
    new StructuredLogger(new InfraLogger().send)
  }

  sealed trait Category {
    val name: String
  }

  object Category {
    implicit val rw: RW[Category] = macroRW

    case object Audit extends Category {
      override val name: String = "audit"
    }
    case object Behaviour extends Category {
      override val name: String = "behaviour"
    }
    case object Error extends Category {
      override val name: String = "error"
    }
  }

  // other fields:
  // - service version
  // - verbosity level
  // - context (e.g. to add class name)
  // - timestamp (or handled by infrastructure logger?)
  case class Message(
      category: Category,
      msg: String,
      service: String,
      hash: String
  )

  object Message {
    implicit val rw: RW[Message] = macroRW
  }
}

class StructuredLogger(logger: String => Unit) {
  def log(message: Message): Unit = {
    logger(write(message))
  }
}
