package hms.m2m.vesak.services.channel

import hms.m2m.vesak.model.LanternColor
import com.typesafe.scalalogging.slf4j.Logging

object ColorIntensityController extends Logging{

  val channels = List[ColorControlChannel](InternalColorControlChannel, HttpColorControlChannel, SmsColorControlChannel)

  def currentIntensity : LanternColor = {

    def multiplyFromWeight(c1 : ColorControlChannel) = {
      LanternColor (
        red = c1.currentIntensity.red * c1.channelWeight.get(),
        green = c1.currentIntensity.green * c1.channelWeight.get(),
        blue = c1.currentIntensity.blue * c1.channelWeight.get())
    }

    def sumColors(c1: LanternColor, c2 : LanternColor) = {
      LanternColor(
        red = c1.red + c2.red,
        green = c1.green + c2.green,
        blue = c1.blue + c2.blue
      )
    }

    def weightedSum(c : LanternColor, i : Int) = {
      LanternColor(
        red = c.red / i,
        green = c.green / i,
        blue = c.blue / i
      )
    }

    val finalColor: LanternColor = channels.map(c1 => multiplyFromWeight(c1)).reduce((c1, c2) => sumColors(c1, c2))

    val weightSum: Int = channels.map(c1 => c1.channelWeight.get()).reduce((c1, c2) => c1 + c2)

    val finalIntensity: LanternColor = weightedSum(finalColor, weightSum)

    logger.debug("Final intensity " + finalIntensity)

    finalIntensity
  }



}
