package controllers.user

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import jp.scala.models.User
import jp.scala.forms.UserForm

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
	    User.insert(form.toUser)
	    Redirect(routes.Application.list)
	  }
	)
  }
  def list = Action {
    Ok(views.html.user.UserList(User.selectAll))
  }
  def modifyById(id:String) = Action {
    val user = User.select(id.toInt).get
    val userform = UserForm(
	  user.id.get,
	  user.login,
	  user.name,
  	  user.email,
   	  user.sex)
    Ok(views.html.user.UserModify(userForm.fill(userform)))
  }
  def modify() = Action { implicit request =>
  	userForm.bindFromRequest.fold(
	  errors => BadRequest(views.html.user.UserModify(errors)),
	  form => {
	    User.update(form.toUser)
	    Redirect(routes.Application.list)
	  }
	)
  }
}
