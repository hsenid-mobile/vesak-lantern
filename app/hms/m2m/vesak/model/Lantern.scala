package hms.m2m.vesak.model

case class LanternColor(red: Int, green: Int, blue: Int)
case class SmsMessage(applicationId: String,
                      sourceAddress: String,
                      message: String,
                      requestId: String)

case class SdpResp(statusCode: String, statusDescription: String)

case class LanternColorResponse(s1 : LanternColor, s2 : LanternColor)

case class Vote(red : Int, green : Int, blue : Int)

case class Percentage(red : Int, green : Int, blue : Int)

case class VoteUpdate(vote : Vote, percentage : Percentage)
