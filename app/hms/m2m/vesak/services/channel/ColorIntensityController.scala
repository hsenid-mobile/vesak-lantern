package hms.m2m.vesak.services.channel

import hms.m2m.vesak.model.LanternColor
import com.typesafe.scalalogging.slf4j.Logging

object ColorIntensityController extends Logging{

  val channels = List[ColorControlChannel](InternalColorControlChannel, HttpColorControlChannel, SmsColorControlChannel)

  def currentIntensity : LanternColor = {

    def compareWeightedSum(c1 : (LanternColor, Int), c2: (LanternColor, Int)) : (LanternColor, Int) = {

      def max(i1 : Int, w1 : Int, i2 : Int, w2 : Int) : Int = {
        if(i1 * w1 > i2 * w2) {
          i1
        } else {
          i2
        }
      }

      (LanternColor(
        red = max(c1._1.red,  c1._2, c2._1.red,  c2._2),
        green = max(c1._1.green, c1._2, c2._1.green, c2._2),
        blue = max(c1._1.blue, c1._2, c2._1.blue, c2._2)
      ) , 1)
    }

    channels.foreach(c1 =>
            logger.debug("Channel " + c1.getClass.getName + " " + "Weight " + c1.channelWeight.get() + " " + "Intensity " + c1.currentIntensity)
    )

    val reduce: (LanternColor, Int) = channels.map(c1 => (c1.currentIntensity, c1.channelWeight.get())).reduce((color1, color2) => compareWeightedSum(color1, color2))

    logger.debug("Final calculated intensity " + reduce._1)

    reduce._1
  }



}
