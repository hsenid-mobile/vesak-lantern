package controllers

import play.api.mvc.{Request, Result, Action, Controller}
import hms.m2m.vesak.services.{VoteStore, LanternService}
import play.api.libs.json.{JsValue, Json}
import hms.m2m.vesak.model.{SdpResp, SmsMessage, LanternColor}
import hms.m2m.vesak.model.JasonFormats._
import com.typesafe.scalalogging.slf4j.Logging
import scala.util.control.Exception._


object LanternApplication extends Controller with Logging {


  def color = Action {
    val current= LanternService.currentColor

    Ok(Json.toJson(current))

  }

  def onSms = Action(parse.json) { request =>

    unmarshalUserResource(request, (sms: SmsMessage) => {
      logger.info(s"Sms message received[${sms}}]")
      val resp = allCatch.either{
        processSms(sms.message)
      }

      resp.left.foreach( th => logger.error("Error processing sms", th))

      Ok(Json.toJson(SdpResp("S1000", "Success")))
    })
  }

  private def processSms(msg: String) {
    msg.split(" ").toList match {
      case kwd :: vote :: rest => {
        logger.info(s"Vote received [${vote}]")

        VoteStore.voteReceived(vote.charAt(0).toString)
      }
      case _ => throw new RuntimeException("Invalid vote")
    }
  }

  private def unmarshalUserResource(request: Request[JsValue],
                                    block: (SmsMessage) => Result): Result = {
    request.body.validate[SmsMessage].fold(
      valid = block,
      invalid = (e => {
        val error = e.mkString
        BadRequest(error)
      })
    )
  }

}