package db

import scala.slick.driver.H2Driver.simple._

/**
 * Created by hps1 on 16.02.14.
 */
object CaseClassMapping extends App {

  // the base query for the Users table
  val users = TableQuery[Users]

  Database.forURL("jdbc:h2:mem:hello", driver = "org.h2.Driver").withSession { implicit session =>

    // create the schema
    users.ddl.create

    // insert two User instances
    users += User("John Doe")
    users += User("Fred Smith")

    // print the users (select * from USERS)
    println(users.list)
  }

}
