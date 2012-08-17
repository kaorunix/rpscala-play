package jp.scala.forms

import play.api.data._
import play.api.data.Forms._
import jp.scala.models.User
import anorm.NotAssigned

case class UserForm(login:String, name:String, email:Option[String], sex:Int) {
  def toUser = User(NotAssigned, login, name, email, sex)
}

