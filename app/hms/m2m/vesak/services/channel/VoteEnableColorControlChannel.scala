package hms.m2m.vesak.services.channel

import akka.actor.ActorSystem
import scala.concurrent.duration.Duration
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger
import hms.m2m.vesak.model.LanternColor

case class VoteCount(r:Int, g:Int, b:Int)

trait VoteEnableColorControlChannel extends ColorControlChannel {

  val red = new AtomicInteger(0)
  val green = new AtomicInteger(0)
  val blue = new AtomicInteger(0)

  resetScheduler

  override def currentIntensity : LanternColor = {

    val l = List(red.get(), green.get(), blue.get())

    def getRank(i : Int) : Int = {
      if(i == 0) {
        return 0
      }

      if (l.max == i) {
        255
      } else if (l.min == i) {
        100
      } else {
        175
      }
    }

    LanternColor(red = getRank(red.get()), green = getRank(green.get()), blue = getRank(blue.get()))
  }

  def vote(color : String) {
    color.toLowerCase match {
      case "r" => red.incrementAndGet
      case "g" => green.incrementAndGet
      case "b" => blue.incrementAndGet
      case _ =>
    }
  }

  def resetScheduler {
    val actorSystem = ActorSystem()
    val scheduler = actorSystem.scheduler
    val task = new Runnable {
      def run() {
        synchronized {
          List(red, green, blue).foreach(color => color.set(0))
        }
      }
    }
    implicit val executor = actorSystem.dispatcher

    scheduler.schedule(
      initialDelay = Duration(5, TimeUnit.SECONDS),
      interval = Duration(10, TimeUnit.SECONDS),
      runnable = task)
  }
}
