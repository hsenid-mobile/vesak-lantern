package hms.m2m.vesak.services

import hms.m2m.vesak.model.{LanternColorResponse, LanternColor}
import hms.m2m.vesak.services.channel.ColorIntensityController

object LanternService {

  def currentColor: LanternColorResponse = {
    val intensity: LanternColor = ColorIntensityController.currentIntensity
    LanternColorResponse(intensity, intensity)
  }
}
