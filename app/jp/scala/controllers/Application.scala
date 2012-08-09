package jp.scala.controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import jp.scala.daos.UserDao

case class UserForm(id:Int, login:String, name:String, email:Option[String], sex:Int)

object Application extends Controller {
  val userForm = Form(
    mapping(
      "id" -> number,
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
	    Redirect(routes.Application.list)
	  }
	)
  }
  def list = Action {
    Ok(views.html.user.UserList(UserDao.selectAll))
  }
  def modifyById(id:String) = Action {
    val userDao = UserDao.select(id.toInt).get
    val userform = UserForm(
	  userDao.id.get,
	  userDao.login,
	  userDao.name,
  	  userDao.email,
   	  userDao.sex)
    Ok(views.html.user.UserModify(userForm.fill(userform)))
  }
  def modify() = Action { implicit request =>
  	userForm.bindFromRequest.fold(
	  errors => BadRequest(views.html.user.UserModify(errors)),
	  form => {
	    UserDao.update(form)
	    Redirect(routes.Application.list)
	  }
	)
  }
  def delete(id:String) = Action {
    UserDao.delete(id.toInt)
    Redirect(routes.Application.list)
  }
}