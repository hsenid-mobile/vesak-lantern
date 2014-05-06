package controllers

import play.api._
import play.api.mvc._
import hms.m2m.vesak.services.StatusScheduler

object Application extends Controller {

  def index = Action {
    StatusScheduler.startScheduler();
    Redirect(routes.LanternApplication.color)
  }

  /**
   * Home page render with the string
   */
  def lantern = Action {
    Ok(views.html.lantern.apply("Vesak Lantern App"))
  }
}