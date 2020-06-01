package com.knoldus.api.subscriber

import java.time.LocalDateTime
import java.util.concurrent.TimeUnit
import akka.actor.ActorRef
import akka.pattern.ask
import akka.stream.scaladsl.Flow
import akka.util.Timeout
import akka.{Done, NotUsed}
import com.knoldus.external.{ExternalService, KafkaMessage, KafkaMessageWithMetadata}
import com.lightbend.lagom.scaladsl.api.broker.Message
import com.lightbend.lagom.scaladsl.broker.kafka.KafkaMetadataKeys
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.util.Try

class KafkaSubscriber(
                       externalService: ExternalService,
                       actorRef1: ActorRef
                     ) extends FlowHelper {

  override val actorRef: ActorRef = actorRef1

  val consumerGroup = "consumer-group-1"

  implicit val timeout = akka.util.Timeout(5, TimeUnit.SECONDS)

  // Start consuming messages from the kafka topic
  // Where inbound topic is the topic from where we need to consume messages
  // subscribe is used to obtain a subscriber to this topic
  // withGroupId returns A copy of this subscriber with the passed group id
  // atLeastOnce : Applies the passed `flow` to the messages processed by this subscriber. Messages are delivered to the passed
  //   * `flow` at least once.
  externalService.inboundTopic.subscribe.withGroupId(consumerGroup).atLeastOnce {
    kafkaMessageFlow
  }

}

trait FlowHelper {

  implicit val timeOut = Timeout(5000.milli)
  val actorRef: ActorRef
  val parallelism = 8

  val terminateFlow: Flow[Any, Done, NotUsed] = Flow[Any].map(_ => Done)

  val forwardKafkaMessageToWorker: Flow[KafkaMessage, Done, NotUsed] = Flow[KafkaMessage]
    .mapAsync(parallelism) { kafkaMessageWithMeta =>
      (actorRef ? kafkaMessageWithMeta)
        .map { _ =>
          println("Got response from actor : " + Done)
          Done
        }
        .recover {
          case ex: Exception =>
            println("Exception found while waiting for processor response: " + ex)
            Done
        }
    }

  val kafkaMessageFlow: Flow[KafkaMessage, Done, NotUsed] = Flow[KafkaMessage]
    .map { msg =>
      println(s"Got message from kafka : [$msg]")
      msg
    }
    .via(forwardKafkaMessageToWorker)
    .via(terminateFlow)
}
