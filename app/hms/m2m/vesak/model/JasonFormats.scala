package hms.m2m.vesak.model

import play.api.libs.json.Json

object JasonFormats {
  implicit val colorFormat = Json.format[LanternColor]
  implicit val smsFormat = Json.format[SmsMessage]
  implicit val sdpRespFormat = Json.format[SdpResp]
  implicit val colorIntensityResponseFormat = Json.format[LanternColorResponse]
  implicit val voteFormat = Json.format[Vote]
  implicit val percentageFormat = Json.format[Percentage]
  implicit val voteUpdateFormat = Json.format[VoteUpdate]
}
