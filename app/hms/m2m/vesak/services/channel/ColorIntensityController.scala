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

    def compareWeightedSum(c1 : (LanternColor, Int), c2: (LanternColor, Int)) : (LanternColor, Int) = {
      (LanternColor(
        red = Math.max(c1._1.red * c1._2, c2._1.red * c2._2),
        green = Math.max(c1._1.green * c1._2, c2._1.green * c2._2),
        blue = Math.max(c1._1.blue * c1._2, c2._1.blue * c2._2)
      ) , 1)
    }

    val reduce: (LanternColor, Int) = channels.map(c1 => (c1.currentIntensity, c1.channelWeight.get())).reduce((color1, color2) => compareWeightedSum(color1, color2))

    reduce._1
  }



}
