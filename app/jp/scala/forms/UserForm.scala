package jp.scala.forms

import play.api.data._
import play.api.data.Forms._
import jp.scala.models.User
import anorm.NotAssigned
import anorm.Id

case class UserForm(id:Int, login:String, name:String, email:Option[String], sex:Int) {
  def toUser = User({if (id == 0) NotAssigned else Id(id)}, login, name, email, sex)
}

