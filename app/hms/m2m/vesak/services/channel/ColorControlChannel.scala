package hms.m2m.vesak.services.channel

import hms.m2m.vesak.model.LanternColor
import java.util.concurrent.atomic.AtomicInteger
import com.typesafe.scalalogging.slf4j.Logging

trait ColorControlChannel extends Logging{

  val channelWeight = new AtomicInteger(1)

}
