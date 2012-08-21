package jp.scala.models

import anorm._
import anorm.SqlParser._
import play.api.db.DB
import play.api.Play.current

case class User(
  id:Pk[Int],
  login:String,
  name:String,
  email:Option[String],
  sex:Int
    ) {
}

object User {
  val simple = {
    get[Pk[Int]]("id") ~
    get[String]("login") ~
    get[String]("name") ~
    get[Option[String]]("email") ~
    get[Int]("sex")  map {
      case id~login~name~email~sex =>
        User(id,
          login,
          name,
          email,
          sex)
    }
  }
  def insert(u:User) = {
    DB.withConnection { implicit c =>
      SQL("""
          insert into User (login, name, email, sex)
          values ({login}, {name}, {email}, {sex})
          """)
          .on("login" -> u.login,
        	  "name" -> u.name,
        	  "email" -> u.email.getOrElse(""),
              "sex" -> u.sex)
              .executeUpdate()
    }
  }
  def selectAll() = {
	DB.withConnection { implicit c =>
	  SQL("SELECT * FROM User").as(User.simple *)
	}
  }
  def select(id:Int) = {
	DB.withConnection { implicit c =>
	  SQL("SELECT * FROM User where id = {id}").on("id" -> id).as(User.simple.singleOpt)
	}
  }
  def update(u:User) = {
    DB.withConnection { implicit c =>
      SQL("""
          update User set
          login={login}, name={name}, email={email}, sex={sex}
          where id={id}
          """)
          .on("login" -> u.login,
        	  "name" -> u.name,
        	  "email" -> u.email,
              "sex" -> u.sex,
              "id" -> u.id.get)
              .executeUpdate()
    }
  }
}