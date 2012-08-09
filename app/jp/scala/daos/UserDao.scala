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
  email:Option[String],
  sex:Int
    ) {
}

object UserDao {
  val simple = {
    get[Pk[Int]]("id") ~
    get[String]("login") ~
    get[String]("name") ~
    get[Option[String]]("email") ~
    get[Int]("sex")  map {
      case id~login~name~email~sex =>
        UserDao(id,
          login,
          name,
          email,
          sex)
    }
  }
  def insert(form:jp.scala.controllers.UserForm) = {
    DB.withConnection { implicit c =>
      SQL("""
          insert into User (login, name, email, sex)
          values ({login}, {name}, {email}, {sex})
          """)
          .on("login" -> form.login,
        	  "name" -> form.name,
        	  "email" -> form.email,
              "sex" -> form.sex)
              .executeUpdate()
    }
  }
  def selectAll() = {
	DB.withConnection { implicit c =>
	  SQL("SELECT * FROM User").as(UserDao.simple *)
	}
  }
  def select(id:Int) = {
	DB.withConnection { implicit c =>
	  SQL("SELECT * FROM User where id = {id}").on("id" -> id).as(UserDao.simple.singleOpt)
	}
  }
  def update(form:jp.scala.controllers.UserForm) = {
    DB.withConnection { implicit c =>
      SQL("""
          update User set
          login={login}, name={name}, email={email}, sex={sex}
          where id={id}
          """)
          .on("login" -> form.login,
        	  "name" -> form.name,
        	  "email" -> form.email,
              "sex" -> form.sex,
              "id" -> form.id)
              .executeUpdate()
    }
  }
  def delete(id:Int) = {
    DB.withConnection { implicit c =>
      SQL("delete User where id = {id}").on("id" -> id).execute()
    }
  }
}