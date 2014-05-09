package hms.m2m.vesak.services.channel

import java.util.concurrent.atomic.AtomicInteger

object SmsColorControlChannel extends VoteEnableColorControlChannel {
  override val channelWeight = new AtomicInteger(100)
}
