import com.typesafe.sbt.SbtMultiJvm
import com.typesafe.sbt.SbtMultiJvm.MultiJvmKeys.MultiJvm

// Refer to http://akka.io/docs/
val akkaVersion = "2.4.17"
val akkaHttpVersion = "10.0.5"

// "akka-persistence-cassandra-3x"
resolvers += "krasserm at bintray" at "http://dl.bintray.com/krasserm/maven"

val project = Project(
  id = "akka-test-cluster-with-multinode",
  base = file(".")
)
.settings(SbtMultiJvm.multiJvmSettings: _*)
.settings(
  name := "akka-sample-multi-node-scala",
  version := "1.0",
  // scalaVersion := "2.12.1",
  scalaVersion := "2.11.8",
  libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,

    "com.typesafe.akka" %% "akka-remote" % akkaVersion,
    "com.typesafe.akka" %% "akka-cluster" % akkaVersion,
    "com.typesafe.akka" %% "akka-cluster-tools" % akkaVersion,
    "com.typesafe.akka" %% "akka-cluster-sharding" % akkaVersion,

    "com.typesafe.akka" %% "akka-persistence" % akkaVersion,
    "com.typesafe.akka" %% "akka-persistence-query-experimental" % akkaVersion,
    "org.iq80.leveldb" % "leveldb" % "0.7",
    "org.fusesource.leveldbjni" % "leveldbjni-all" % "1.8",
    "commons-io" % "commons-io" % "2.4",
    "com.github.krasserm" %% "akka-persistence-cassandra-3x" % "0.6",

    "com.typesafe.akka" %% "akka-http-core" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,

    "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test",
    "com.typesafe.akka" %% "akka-multi-node-testkit" % akkaVersion % "test",
    "org.scalatest" %% "scalatest" % "3.0.1" % "test",

    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
    "ch.qos.logback" % "logback-classic" % "1.1.2"

  ),
  // make sure that MultiJvm test are compiled by the default test compilation
  compile in MultiJvm <<= (compile in MultiJvm) triggeredBy (compile in Test),
  // disable parallel tests
  parallelExecution in Test := false,
  // make sure that MultiJvm tests are executed by the default test target,
  // and combine the results from ordinary test and multi-jvm tests
  executeTests in Test <<= (executeTests in Test, executeTests in MultiJvm) map {
    case (testResults, multiNodeResults)  =>
      val overall =
        if (testResults.overall.id < multiNodeResults.overall.id)
          multiNodeResults.overall
        else
          testResults.overall
      Tests.Output(overall,
        testResults.events ++ multiNodeResults.events,
        testResults.summaries ++ multiNodeResults.summaries)
  },
  licenses := Seq(("CC0", url("http://creativecommons.org/publicdomain/zero/1.0")))
)
.configs (MultiJvm)

//mainClass in Global := Some("aia.cluster.words.Main")

//assemblyJarName in assembly := "words-node.jar"

mainClass in Global := Some("aia.persistence.sharded.ShardedMain")

assemblyJarName in assembly := "persistence-examples.jar"
