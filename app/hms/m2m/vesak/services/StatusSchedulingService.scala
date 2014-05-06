package hms.m2m.vesak.services

import com.typesafe.scalalogging.slf4j.Logging
import java.util.concurrent.atomic.AtomicInteger
import akka.actor.ActorSystem
import scala.concurrent.duration.Duration
import java.util.concurrent.TimeUnit


class SchedulingService  {

}

object StatusScheduler extends Logging {

  def startScheduler() {
    val actorSystem = ActorSystem()
    val scheduler = actorSystem.scheduler
    val task = new Runnable { def run() {
      // TODO implement get status
      logger.debug("Running Status Scheduler.")
    } }

    implicit val executor = actorSystem.dispatcher

    scheduler.schedule(
      initialDelay = Duration(5, TimeUnit.SECONDS),
      interval = Duration(10, TimeUnit.SECONDS),
      runnable = task)


  }
}