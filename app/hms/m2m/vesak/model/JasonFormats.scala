package hms.m2m.vesak.model

import play.api.libs.json.Json

object JasonFormats {
  implicit val colorFormat = Json.format[LanternColor]
  implicit val smsFormat = Json.format[SmsMessage]
}
