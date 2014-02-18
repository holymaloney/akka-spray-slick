package caseclasses

import java.sql.Timestamp

/**
 * Created by hps1 on 17.02.14.
 */


case class Question(id: Option[Int], authorId: Int, content: String, createdDate: Timestamp, lastEditedTS: Option[Timestamp], lastEditedBy: Option[Int])

case class Author(id: Option[Int] = None, name: String, createdDate: Timestamp, score: Int)

case class Answer (id: Option[Int] = None, questionId: Int, authorId: Int, content: String, createdDate: Timestamp, lastEditedTS: Option[Timestamp] = None, lastEditedBy: Option[Int] = None, votes: Int = 0)
