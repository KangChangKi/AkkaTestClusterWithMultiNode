package aia.cluster
package words

import akka.actor._
import akka.cluster.routing._
import akka.routing._

/*
There is also a ClusterRouterGroup, which has a ClusterRouterGroupSettings similar to how the ClusterRouterPool is set up.
The actors that are being routed to need to be running before a group router can send messages to them.
The words cluster can have many master role nodes.
Every master role starts up with a JobReceptionist actor.
In the case where you want to send messages to every JobReceptionist, you could use a ClusterRouterGroup,
 for instance sending a message to the JobReceptionists to cancel all currently running jobs in the cluster.
Listing 13.16, "Send messages to all JobReceptionists in the cluster" shows how you can create a router that
 looks up JobReceptionists on master role nodes in the cluster
 (an example can be found in src/main/scala/aia/cluster/words/ReceptionistRouterLookup.scala):
 */

trait ReceptionistRouterLookup {
  this: Actor =>

  def receptionistRouter = context.actorOf(
    ClusterRouterGroup(
      BroadcastGroup(Nil),
      ClusterRouterGroupSettings(
        totalInstances = 100,
        routeesPaths = List("/user/receptionist"),
        allowLocalRoutees = true,
        useRole = Some("master")
      )
    ).props(),
    name = "receptionist-router")
}
