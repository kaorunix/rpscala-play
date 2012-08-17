package jp.scala.controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import jp.scala.daos.UserDao

object Application extends Controller {
  val userForm = Form(
    mapping(
      "login" -> nonEmptyText,
      "name" -> nonEmptyText,
      "email" -> optional(email),
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
	    UserDao.insert(form)
	    Redirect(routes.Application.index)
	  }
	)
  }
  def list = Action {
    Ok(views.html.user.UserList(UserDao.selectAll))
  }
  def selectAll() = {
    DB.withConnection { implicit c =>
      SQL("SELECT * FROM User").as(UserDao.simple *)
    }
  }
}
