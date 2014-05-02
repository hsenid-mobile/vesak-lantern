package controllers

import play.api.mvc.{Request, Result, Action, Controller}
import hms.m2m.vesak.services.LanternService
import play.api.libs.json.{JsValue, Json}
import hms.m2m.vesak.model.{SmsMessage, LanternColor}
import hms.m2m.vesak.model.JasonFormats._

object LanternApplication extends Controller {


  def color = Action {
    val current= LanternService.currentColor

    Ok(Json.toJson(current))

  }

  def onSms = Action(parse.json) { request =>

    unmarshalUserResource(request, (sms: SmsMessage) => {
      println("=============>" + sms)
      Ok(Json.toJson(sms))
    })
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