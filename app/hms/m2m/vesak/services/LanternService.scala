package hms.m2m.vesak.services

import hms.m2m.vesak.model.LanternColor
import hms.m2m.vesak.services.channel.ColorIntensityController

object LanternService {

  def currentColor: LanternColor = ColorIntensityController.currentIntensity
}
