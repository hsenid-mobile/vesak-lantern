package controllers

import play.api.mvc.{Request, Result, Action, Controller}
import hms.m2m.vesak.services.LanternService
import play.api.libs.json.{JsValue, Json}
import hms.m2m.vesak.model.{SdpResp, SmsMessage}
import hms.m2m.vesak.model.JasonFormats._
import com.typesafe.scalalogging.slf4j.Logging
import scala.util.control.Exception._
import hms.m2m.vesak.services.channel.{HttpColorControlChannel, SmsColorControlChannel}


object LanternApplication extends Controller with Logging {


  def color = Action {

    Ok(Json.toJson(LanternService.currentColor))

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

        if("reset".equals(vote.toLowerCase)) {
          SmsColorControlChannel.reset
        } else {
          SmsColorControlChannel.vote(vote.charAt(0).toString)
        }
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