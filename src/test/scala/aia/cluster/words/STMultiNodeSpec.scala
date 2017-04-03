package aia.cluster.words

import akka.remote.testkit.MultiNodeSpecCallbacks
import org.scalatest._

trait STMultiNodeSpec extends MultiNodeSpecCallbacks
  with WordSpecLike
  with Matchers
  with BeforeAndAfterAll {

  override def beforeAll() = multiNodeSpecBeforeAll()

  override def afterAll() = multiNodeSpecAfterAll()
}
