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
  def insert(u:User) = {
    DB.withConnection { implicit c =>
      SQL("""
          insert into User (login, name, mail, sex)
          values ({login}, {name}, {mail}, {sex})
          """)
          .on("login" -> u.login,
        	  "name" -> u.name,
        	  "mail" -> u.email.getOrElse(""),
              "sex" -> u.sex)
              .executeUpdate()
    }
  }
}