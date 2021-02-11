package domain

import domain.User.{DomainError, EmptyName, InvalidName}
import shared.structuredlogger.StructuredLogger
import shared.structuredlogger.StructuredLogger.{Category, Message}

/**
  * A logger oriented towards the domain that can be designed as any other 'UI'.
  *
  * Logging in application code always happens through this logger.
 * (No direct logging via 'infrastructure logger'.)
  * */
trait Logger {
  def invalidPasswordAttempt(user: User): Unit
  def error(error: DomainError): Unit
}

/**
 * In the implementation we decide what information is logged.
 * Makes use of categories to make differentiate in confidentiality
 * or target different 'audiences'.
 * */
object Logger {

  // We use a structured logger.
  private val logger = StructuredLogger.default()

  def default(serviceName: String): Logger = new Logger {

    override def invalidPasswordAttempt(user: User): Unit = ???

    override def error(error: DomainError): Unit = {
      error match {
        case EmptyName => {
          // log as error
          logger.log(
            Message(
              Category.Error,
              "Attempt with empty string",
              serviceName,
              ""
            )
          )
        }
        case InvalidName(illegalName) => {
          // log to audit log, wrong inserted name may be sensitive
          logger.log(
            Message(
              Category.Audit,
              s"User creation attempt with name $illegalName",
              serviceName,
              illegalName.reverse
            )
          )
        }
      }
    }
  }
}
