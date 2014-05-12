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

  var nextIntensity = LanternColor(red = 0, green = 0, blue = 0)

  resetScheduler
  counterUpdateScheduler

  override def currentIntensity : LanternColor = {
    nextIntensity
  }

  def vote(color : String) {
    color.toLowerCase match {
      case "r" => red.incrementAndGet
      case "g" => green.incrementAndGet
      case "b" => blue.incrementAndGet
      case _ =>
    }
  }

  def counterUpdateScheduler {
    val actorSystem = ActorSystem()
    val scheduler = actorSystem.scheduler
    val task = new Runnable {
      def run() {
        synchronized {
          calculateAndRestForNextCycle
        }
      }
    }
    implicit val executor = actorSystem.dispatcher

    scheduler.schedule(
      initialDelay = Duration(5, TimeUnit.SECONDS),
      interval = Duration(2, TimeUnit.SECONDS),
      runnable = task)
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
      interval = Duration(1, TimeUnit.HOURS),
      runnable = task)
  }


  def calculateAndRestForNextCycle {
    val l = List(red.get(), green.get(), blue.get())

    def getRank(i : Int) : Int = {
      if(i == 0) {
        return 0
      }

      if (l.max == i) {
        255
      } else {
        (i * 255) / l.max
      }
    }
    nextIntensity = LanternColor(red = getRank(red.get()), green = getRank(green.get()), blue = getRank(blue.get()))
  }

}
