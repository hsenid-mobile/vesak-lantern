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

  def reset {
    red.set(0)
    green.set(0)
    blue.set(0)
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
          List(red, green, blue).foreach(color => color.set((color.get() * 999) / 1000))
        }
      }
    }
    implicit val executor = actorSystem.dispatcher

    scheduler.schedule(
      initialDelay = Duration(5, TimeUnit.SECONDS),
      interval = Duration(10, TimeUnit.MINUTES),
      runnable = task)
  }

}
