package jp.scala.controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import jp.scala.models.User

case class UserForm(login:String, name:String, email:String, sex:Int)

object Application extends Controller {
  val userForm = Form(
    mapping(
      "login" -> nonEmptyText,
      "name" -> nonEmptyText,
      "email" -> email,
      "sex" -> number
    )(UserForm.apply)(UserForm.unapply)
  )
  def index = Action {
    Ok(views.html.user.UserCreate(userForm))
  }
  def create = Action { implicit request =>
  	userForm.bindFromRequest.fold(
	  errors => BadRequest(views.html.user.UserCreate(errors)),
	  form => {
	    User.insert(form)
	    Redirect(routes.Application.index)
	  }
	)
  }
}