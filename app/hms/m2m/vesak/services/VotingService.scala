package hms.m2m.vesak.services

import java.util.concurrent.atomic.AtomicInteger
import com.typesafe.scalalogging.slf4j.Logging

class VotingService {




}

case class VoteCount(r:Int, g:Int, b:Int)

object VoteStore extends Logging {
  val red: AtomicInteger = new AtomicInteger(0)
  val green: AtomicInteger = new AtomicInteger(0)
  val blue: AtomicInteger = new AtomicInteger(0)


  def voteReceived(vote: String) {
    vote.toLowerCase match {
      case "r" => red.incrementAndGet
      case "g" => green.incrementAndGet
      case "b" => blue.incrementAndGet
      case _ => new RuntimeException("Invalid Vote")
    }

    logger.info(s"Current vote count[${currentVoteCount}}]")
  }

  def currentVoteCount: VoteCount = VoteCount(red.get, green.get, blue.get)
}
