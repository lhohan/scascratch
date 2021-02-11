package domain

import domain.User.DomainError

class UserService {
  def create(userName: String): Either[DomainError, User] = User(userName)
}

case class User(name: String)

object User {

  trait DomainError
  case class InvalidName(illegalName: String) extends DomainError
  case object EmptyName extends DomainError

  def apply(name: String): Either[DomainError, User] = {
    if(name.isBlank) Left(EmptyName)
    else if(name.head.isLower) Left(InvalidName(name))
         else Right(new User(name))
  }
}