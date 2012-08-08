package jp.scala.daos

import anorm._
import anorm.SqlParser._
import play.api.db.DB
import play.api.Play.current

object UserDao {
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
}