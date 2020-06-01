package com.knoldus.api.impl

import akka.Done
import akka.actor.{Actor, ActorRef, ActorSystem, Props}

class ProcessorActor extends Actor{

  override def receive: Receive = {
    case msg =>
      println("Message found: " + msg)
      // Processing logic
      sender() ! Done
  }

}

object ProcessorActor{

  def apply(system: ActorSystem): ActorRef = {
    system.actorOf(Props[ProcessorActor])
  }

}
