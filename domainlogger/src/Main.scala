import domain.{Logger, UserService}

object Main extends App {
  val service = new UserService
  val logger: Logger = Logger.create()

  service.create("").fold(logger.error, _ => ())
  service.create("hans").fold(logger.error, _ => ())
  service.create("Hans").fold(logger.error, _ => ())


}
