package controllers

import play.api._
import play.api.mvc._
import hms.m2m.vesak.services.StatusScheduler
import java.util.Date
import com.typesafe.scalalogging.slf4j.Logging
import hms.m2m.vesak.services.channel.HttpColorControlChannel

//import java.lang.{StringBuilder, String, Integer}
import scala.util.Random
import scala.Predef.String
import scala.StringBuilder

object Application extends Controller with Logging{

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

  //
  def redButtonOnclick = Action {
    HttpColorControlChannel.vote("r")
    Ok("")
  }

  def greenButtonOnclick = Action {
    HttpColorControlChannel.vote("g")
    Ok("")
  }

  def blueButtonOnclick = Action {
    HttpColorControlChannel.vote("b")
    Ok("")
  }

  def getVotes = Action {
    Ok("" + getRandomValue)
  }

  def changeColor = Action {
    val hexValue = toHex(getRandomValue, getRandomValue, getRandomValue)
    Ok(hexValue)
  }

  def getOpacity = Action {
    Ok("" + getRandomOpacityValue)
  }

  def toHex(r: Int, g: Int, b: Int): String = {
    "#" + toBrowserHexValue(r) + toBrowserHexValue(g) + toBrowserHexValue(b)
  }

  private def toBrowserHexValue(number: Int): String = {
    val builder = new StringBuilder
    builder ++= Integer.toHexString(number & 0xff)
    while (builder.length < 2) {
      builder.append("0")
    }
    builder.toString().toUpperCase
  }

  def getRandomValue: Int = {
    val rnd = new scala.util.Random
    rnd.nextInt(100)
  }

  def getRandomOpacityValue: Double = {
    val rnd = new scala.util.Random
    rnd.nextDouble()
  }

}