package hms.m2m.vesak.services.channel

import akka.actor.ActorSystem
import scala.concurrent.duration.Duration
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger
import hms.m2m.vesak.model.LanternColor

object InternalColorControlChannel extends ColorControlChannel{

  override val channelWeight = new AtomicInteger(1)

  internalCycle

  val cycleTime = new AtomicInteger(5)

  val fullCycleDegree = 360

  val piDegreeConversion = (2 * Math.PI / fullCycleDegree)

  val phaseR = new AtomicInteger(0)
  val phaseG = new AtomicInteger(fullCycleDegree / 3)
  val phaseB = new AtomicInteger(2 * fullCycleDegree / 3)

  val maxIntensity = 255
  val minIntensity = 0

  override def currentIntensity : LanternColor =  {

    def calculateIntensity(i : Int) : Int  = {
      (minIntensity + Math.abs(Math.sin(piDegreeConversion * i)) * (maxIntensity - minIntensity)).toInt
    }

    val color: LanternColor = LanternColor(red = calculateIntensity(phaseR.get()),
      green = calculateIntensity(phaseG.get()),
      blue = calculateIntensity(phaseB.get()))

    logger.debug("Intensity form channel " + this.getClass.getName + " " + color)

    color
  }



  def internalCycle {
    val actorSystem = ActorSystem()
    val scheduler = actorSystem.scheduler
    val task = new Runnable {
      def run() {
        synchronized {
          List(phaseR, phaseG, phaseB).foreach(phase => {
            if(phase.get() % fullCycleDegree == 0) {
              phase.set(0)
            }
            else {
              phase.set(phase.get() + fullCycleDegree/cycleTime.get())
            }
          })
        }
      }
    }
    implicit val executor = actorSystem.dispatcher

    scheduler.schedule(
      initialDelay = Duration(1, TimeUnit.SECONDS),
      interval = Duration(1, TimeUnit.SECONDS),
      runnable = task)
  }



}
