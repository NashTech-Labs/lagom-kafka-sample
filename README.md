# lagom-kafka-template
This is a sample project that consumes kafka messages using lagom and forward the messages to akka actor using akka stream and process them using akka actor.

<!-- wp:heading {"level":3} -->
<h3>Run the App:</h3>
<!-- /wp:heading -->

<!-- wp:paragraph {"align":"justify"} -->
<p class="has-text-align-justify">Here are the steps to run the application to consume messages from kafka topic:</p>
<!-- /wp:paragraph -->

<!-- wp:paragraph -->
<p>Step 1. Clone the repo from here.</p>
<!-- /wp:paragraph -->

<!-- wp:code -->
<pre class="wp-block-code"><code></code></pre>
<!-- /wp:code -->

<!-- wp:paragraph -->
<p>Step 2. Run sbt from root directory.</p>
<!-- /wp:paragraph -->

<!-- wp:paragraph -->
<p>Step 3. Change project to processor-impl</p>
<!-- /wp:paragraph -->

<!-- wp:code -->
<pre class="wp-block-code"><code>project processor-impl</code></pre>
<!-- /wp:code -->

<!-- wp:paragraph -->
<p>Step 4. Start embeded cassandra and kafka </p>
<!-- /wp:paragraph -->

<!-- wp:code -->
<pre class="wp-block-code"><code>sbt:processor-impl> lagomKafkaStart
sbt:processor-impl> lagomCassandraStart</code></pre>
<!-- /wp:code -->

<!-- wp:paragraph -->
<p>Step 5. Run application</p>
<!-- /wp:paragraph -->

<!-- wp:code -->
<pre class="wp-block-code"><code>sbt:processor-impl> run</code></pre>
<!-- /wp:code -->

<!-- wp:paragraph {"align":"justify"} -->
<p class="has-text-align-justify">Step 6. Start kafka console producer to populate sample messages fro root kafka directory on a separate terminal</p>
<!-- /wp:paragraph -->

<!-- wp:code -->
<pre class="wp-block-code"><code>bin/kafka-console-producer.sh --broker-list localhost:9092 --topic external-messages
>{"name":"girish","date":"267 July 2020","message":"Hello"}
>{"name":"girish","date":"267 July 2020","message":"Hello"}</code></pre>
<!-- /wp:code -->

<!-- wp:paragraph -->
<p>The output you will see is log messages for processing done:</p>
<!-- /wp:paragraph -->

<!-- wp:code -->
<pre class="wp-block-code"><code>22:17:06.211 &#91;info] com.lightbend.lagom.internal.persistence.cluster.ClusterStartupTaskActor &#91;sourceThread=processor-impl-application-akka.actor.default-dispatcher-5, akkaTimestamp=16:47:06.211UTC, akkaSource=akka.tcp://processor-impl-application@127.0.0.1:34681/user/cassandraOffsetStorePrepare-singleton/singleton/cassandraOffsetStorePrepare, sourceActorSystem=processor-impl-application] - Cluster start task cassandraOffsetStorePrepare done.
Got message from kafka : &#91;KafkaMessage(girish,267 July 2020,Hello)]
Message found: KafkaMessage(girish,267 July 2020,Hello)
Got response from actor : Done
</code></pre>
<!-- /wp:code -->

<!-- wp:paragraph {"align":"justify"} -->
<p class="has-text-align-justify">You can play with this template more and more with different implementations of actor and message flow. </p>
<!-- /wp:paragraph -->
