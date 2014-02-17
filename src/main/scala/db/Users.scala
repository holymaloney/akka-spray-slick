package db
import scala.slick.driver.H2Driver.simple._
import scala.slick.lifted.{ProvenShape, ForeignKeyQuery}

/**
 * Created by hps1 on 16.02.14.
 */
class Users(tag: Tag) extends Table[User](tag, "USERS") {
  // Auto Increment the id primary key column
  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
  // The name can't be null
  def name = column[String]("NAME", O.NotNull)
  // the * projection (e.g. select * ...) auto-transform the tupled column values to / from a User
  def * = (name, id.?) <> (User.tupled, User.unapply)
}
