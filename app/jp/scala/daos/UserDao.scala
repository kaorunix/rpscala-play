package jp.scala.daos

import anorm._
import anorm.SqlParser._
import play.api.db.DB
import play.api.Play.current
import play.api.data.Form

case class UserDao(
  id:Pk[Int],
  login:String,
  name:String,
  mail:Option[String],
  sex:Int
    ) {
}

object UserDao {
  val simple = {
    get[Pk[Int]]("id") ~
    get[String]("login") ~
    get[String]("name") ~
    get[Option[String]]("mail") ~
    get[Int]("sex")  map {
      case id~login~name~mail~sex =>
        UserDao(id,
          login,
          name,
          mail,
          sex)
    }
  }
  def insert(form:jp.scala.controllers.UserForm) = {
    DB.withConnection { implicit c =>
      SQL("""
          insert into User (login, name, mail, sex)
          values ({login}, {name}, {mail}, {sex})
          """)
          .on("login" -> form.login,
        	  "name" -> form.name,
        	  "mail" -> form.email,
              "sex" -> form.sex)
              .executeUpdate()
    }
  }
  def selectAll() = {
	DB.withConnection { implicit c =>
	  SQL("SELECT * FROM User").as(UserDao.simple *)
	}
  }
}