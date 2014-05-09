package hms.m2m.vesak.services.channel

import java.util.concurrent.atomic.AtomicInteger

object HttpColorControlChannel extends VoteEnableColorControlChannel{
  override val channelWeight = new AtomicInteger(10)
}
