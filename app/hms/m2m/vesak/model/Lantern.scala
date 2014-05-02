package hms.m2m.vesak.model

case class LanternColor(red: Int, green: Int, blue: Int)
case class SmsMessage(applicationId: String,
                      sourceAddress: String,
                      message: String,
                      requestId: String)


