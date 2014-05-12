package controllers

import play.api._
import play.api.mvc._
import hms.m2m.vesak.services.{LanternService, StatusScheduler}
import play.api.libs.json.{JsValue, Json}
import java.util.Date
import hms.m2m.vesak.model.JasonFormats._
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

  def getVoteNum = Action {
    val voteNumBuilder = new StringBuilder
    voteNumBuilder.append(getRandomValue).append(":").append(getRandomValue).append(":").append(getRandomValue)
    Ok("" + voteNumBuilder.toString())
  }

  def getVotePercentage = Action {
    val votePercentageBuilder = new StringBuilder
    votePercentageBuilder.append(getRandomValue).append(":").append(getRandomValue).append(":").append(getRandomValue)
    Ok("" + votePercentageBuilder.toString())
  }

  def changeColor = Action {
    val hexValue = toHex(getRandomValue, getRandomValue, getRandomValue)
    Ok(hexValue+":"+"85")
  }

  def getLanternColor = Action {
    Ok(""+Json.toJson(LanternService.currentColor))
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