package db

import java.sql.Timestamp

/**
 * Created by hps1 on 17.02.14.
 */
case class Author(id: Option[Int] = None, name: String, createdDate: Timestamp, score: Int)
