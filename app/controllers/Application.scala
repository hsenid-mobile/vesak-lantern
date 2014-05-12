package controllers

import play.api._
import play.api.mvc._
import hms.m2m.vesak.services.{LanternService, StatusScheduler}
import play.api.libs.json.{JsValue, Json}
import java.util.Date
import hms.m2m.vesak.model.JasonFormats._
import com.typesafe.scalalogging.slf4j.Logging
import hms.m2m.vesak.services.channel.{SmsColorControlChannel, HttpColorControlChannel}
import java.util.concurrent.atomic.AtomicInteger
import scala.concurrent.stm
import hms.m2m.vesak.model.{Percentage, Vote, VoteUpdate}

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

  /*def getVoteNum = Action {
    val voteNumBuilder = new StringBuilder
    voteNumBuilder.append(getRandomValue).append(":").append(getRandomValue).append(":").append(getRandomValue)
    Ok("" + voteNumBuilder.toString())
  }

  def getVotePercentage = Action {
    val votePercentageBuilder = new StringBuilder
    votePercentageBuilder.append(getRandomValue).append(":").append(getRandomValue).append(":").append(getRandomValue)
    Ok("" + votePercentageBuilder.toString())
  }*/
  def reset = Action {
    List(SmsColorControlChannel, HttpColorControlChannel).foreach(c => c.reset)
    Ok("")
  }

  def getVotes = Action {

    val colors: List[(AtomicInteger, AtomicInteger, AtomicInteger)] = List(HttpColorControlChannel, SmsColorControlChannel).map(c => (c.red, c.blue, c.green))
    val totalColorCount: (AtomicInteger, AtomicInteger, AtomicInteger) = colors.reduce((c1, c2) => (new AtomicInteger(c1._1.get() + c2._1.get()), new AtomicInteger(c1._2.get() + c2._2.get()), new AtomicInteger(c1._3.get() + c2._3.get())))

    val totalCount: Int = totalColorCount._1.get() + totalColorCount._2.get() + totalColorCount._3.get()



    val percentage: Percentage = totalCount match {
      case 0 => Percentage(red = 0, green = 0, blue = 0)
      case _ => Percentage(red = (totalColorCount._1.get() * 100) / totalCount, green = (totalColorCount._2.get() * 100) / totalCount, blue = (totalColorCount._3.get() * 100) / totalCount)
    }
    Ok("" + Json.toJson(VoteUpdate(Vote(red = totalColorCount._1.get(), green = totalColorCount._2.get(), blue = totalColorCount._3.get()),
      percentage
    )))
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