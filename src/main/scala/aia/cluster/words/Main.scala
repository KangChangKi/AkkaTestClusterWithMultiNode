package aia.cluster
package words

import com.typesafe.config.ConfigFactory
import akka.actor.{Props, ActorSystem}
import akka.cluster.Cluster

import JobReceptionist.JobRequest

/*
There is no "application.conf" file which is the default configuration file.
Instead it expects to take custom configuration file when it launches like:

java -DPORT=2551 \
	 -Dconfig.resource=/seed.conf \
	 -jar target/words-node.jar

java -DPORT=2554 \
	 -Dconfig.resource=/master.conf \
	 -jar target/words-node.jar

java -DPORT=2555 \
	 -Dconfig.resource=/worker.conf \
	 -jar target/words-node.jar

java -DPORT=2556 \
	 -Dconfig.resource=/worker.conf \
	 -jar target/words-node.jar
 */

object Main extends App {
  val config = ConfigFactory.load()
  val system = ActorSystem("words", config)

  println(s"Starting node with roles: ${Cluster(system).selfRoles}")

  if (system.settings.config.getStringList("akka.cluster.roles").contains("master")) {
    Cluster(system).registerOnMemberUp {
      val receptionist = system.actorOf(Props[JobReceptionist], "receptionist")
      println("Master node is ready.")

      val text = List("this is a test", "of some very naive word counting", "but what can you say", "it is what it is")
      receptionist ! JobRequest("the first job", (1 to 100000).flatMap(i => text ++ text).toList)
      system.actorOf(Props(new ClusterDomainEventListener), "cluster-listener")
    }
  }
}
