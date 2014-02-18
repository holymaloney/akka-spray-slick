package db

import scala.slick.driver.H2Driver.simple._
import scala.slick.lifted.{ProvenShape, ForeignKeyQuery}
import java.sql.Timestamp
import caseclasses.{Author, Answer, Question}

class Questions(tag: Tag) extends Table[Question](tag, "QUESTIONS") {
  def id = column[Int]("QUEST_ID", O.PrimaryKey, O.AutoInc)
  def authorId = column[Int]("AUTHOR_ID", O.NotNull)
  def content = column[String]("CONTENT", O.NotNull)
  def createdDate = column[Timestamp]("CREATED_DATE", O.NotNull)
  def lastEditedTS = column[Timestamp]("LAST_EDITED_TS", O.Nullable)
  def lastEditedBy = column[Int]("LAST_EDITED_BY", O.Nullable)

 // def tags = ???

  def * = (id.?, authorId, content, createdDate, lastEditedTS.?, lastEditedBy.?) <> (Question.tupled, Question.unapply)

  def author = foreignKey("Q_AUTH_FK", authorId, TableQuery[Authors])(_.id)
  def answers = TableQuery[Answers] where (_.questionID === id)
}

class Answers(tag: Tag) extends Table[Answer](tag, "ANSWERS") {
  def id: Column[Int] = column[Int]("ANSW_ID", O.PrimaryKey, O.AutoInc)
  def questionID: Column[Int] = column[Int]("QUESTION_ID", O.NotNull)
  def authorId: Column[Int] = column[Int]("AUTHOR_ID", O.NotNull)
  def content: Column[String] = column[String]("CONTENT", O.NotNull)
  def createdDate: Column[Timestamp] = column[Timestamp]("CREATED_DATE", O.NotNull)
  def lastEditedTS: Column[Timestamp] = column[Timestamp]("LAST_EDITED", O.Nullable)
  def lastEditedBy: Column[Int] = column[Int]("EDITED_BY", O.Nullable)
  def votes: Column[Int] = column[Int]("VOTES", O.Default(0))

  def *  = (id.?, questionID, authorId, content, createdDate, lastEditedTS.?, lastEditedBy.?, votes) <> (Answer.tupled, Answer.unapply )

  def question: ForeignKeyQuery[Questions, Question] =
    foreignKey("QUESTION_FK", questionID, TableQuery[Questions])(_.id)
  def author: ForeignKeyQuery[Authors, Author] =
    foreignKey("A_AUTH_FK", authorId, TableQuery[Authors])(_.id)
  def editedByAuthor: ForeignKeyQuery[Authors, Author] =
    foreignKey("EDITEDBY_FK", authorId, TableQuery[Authors])(_.id)
}
class Authors(tag: Tag) extends Table[Author](tag, "AUTHORS") {
  def id: Column[Int] = column[Int]("AUTH_ID", O.PrimaryKey, O.AutoInc)
  def name: Column[String] = column[String]("NAME", O.NotNull)
  def createdDate: Column[Timestamp] = column[Timestamp]("CREATED_DATE", O.NotNull)
  def score: Column[Int] = column[Int]("SCORE", O.NotNull)
//  def profileImage: ???

  def * = (id.?, name, createdDate, score) <> (Author.tupled, Author.unapply )

}

// A db.Suppliers table with 6 columns: id, name, street, city, state, zip
class Suppliers(tag: Tag) extends Table[(Int, String, String, String, String, String)](tag, "SUPPLIERS") {
  def id: Column[Int] = column[Int]("SUP_ID", O.PrimaryKey) // This is the primary key column
  def name: Column[String] = column[String]("SUP_NAME")
  def street: Column[String] = column[String]("STREET")
  def city: Column[String] = column[String]("CITY")
  def state: Column[String] = column[String]("STATE")
  def zip: Column[String] = column[String]("ZIP")
  
  // Every table needs a * projection with the same type as the table's type parameter
  def * : ProvenShape[(Int, String, String, String, String, String)] = (id, name, street, city, state, zip)
}

// A db.Coffees table with 5 columns: name, supplier id, price, sales, total
class Coffees(tag: Tag) extends Table[(String, Int, Double, Int, Int)](tag, "COFFEES") {
  def name: Column[String] = column[String]("COF_NAME", O.PrimaryKey)
  def supID: Column[Int] = column[Int]("SUP_ID")
  def price: Column[Double] = column[Double]("PRICE")
  def sales: Column[Int] = column[Int]("SALES")
  def total: Column[Int] = column[Int]("TOTAL")
  
  def * : ProvenShape[(String, Int, Double, Int, Int)] = (name, supID, price, sales, total)
  
  // A reified foreign key relation that can be navigated to create a join
  def supplier: ForeignKeyQuery[Suppliers, (Int, String, String, String, String, String)] = 
    foreignKey("SUP_FK", supID, TableQuery[Suppliers])(_.id)
}
