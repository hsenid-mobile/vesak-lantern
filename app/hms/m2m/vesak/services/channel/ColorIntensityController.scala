package hms.m2m.vesak.services.channel

import hms.m2m.vesak.model.LanternColor
import com.typesafe.scalalogging.slf4j.Logging
import akka.actor.ActorSystem
import scala.concurrent.duration.Duration
import java.util.concurrent.TimeUnit

object ColorIntensityController extends Logging{

  val channels = List[VoteEnableColorControlChannel](HttpColorControlChannel, SmsColorControlChannel)

  var nextIntensity = LanternColor(red = 0, green = 0, blue = 0)

  counterUpdateScheduler

  def currentIntensity : LanternColor = {
      nextIntensity
  }

  def counterUpdateScheduler {
    val actorSystem = ActorSystem()
    val scheduler = actorSystem.scheduler
    val task = new Runnable {
      def run() {
        synchronized {
          nextIntensity = calculateForNextCycle
        }
      }
    }
    implicit val executor = actorSystem.dispatcher

    scheduler.schedule(
      initialDelay = Duration(5, TimeUnit.SECONDS),
      interval = Duration(2, TimeUnit.SECONDS),
      runnable = task)
  }

  def calculateForNextCycle : LanternColor = {

    val colors: List[(Int, Int, Int)] = channels.map(c => (c.red.get() * c.channelWeight.get(),
                                                            c.green.get() * c.channelWeight.get(),
                                                            c.blue.get() * c.channelWeight.get()))

    val finalWeightedVotes: (Int, Int, Int) = colors.reduce((c1, c2) => (c1._1 + c2._1, c1._2 + c2._2, c1._3 + c2._3))

    val votes = List(finalWeightedVotes._1, finalWeightedVotes._2, finalWeightedVotes._3)

    val totalVotes = votes.reduce((c1, c2) => c1 + c2)

    if(totalVotes == 0) {
      InternalColorControlChannel.currentIntensity
    } else {
      def getRank(i : Int) : Int = {
        if(votes.max == i) {
          255
        } else {
          (255 * i) / votes.max
        }
      }

      LanternColor(red = getRank(finalWeightedVotes._1), green = getRank(finalWeightedVotes._2), blue = getRank(finalWeightedVotes._3))
    }

  }


}
