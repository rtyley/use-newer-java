name := "use-newer-java"

description := "A small lib to give your users a friendly message that they're using a version of Java that's too old."

scalaVersion := "2.13.5"

libraryDependencies ++= Seq(
  "junit" % "junit" % "4.13.2" % Test,
  "com.novocode" % "junit-interface" % "0.11" % Test
)

organization := "com.madgag"
licenses := Seq("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0"))
publishTo := sonatypePublishToBundle.value
scmInfo := Some(ScmInfo(
  url("https://github.com/rtyley/use-newer-java"),
  "scm:git:git@github.com:rtyley/use-newer-java.git"
))


import ReleaseTransformations._

releaseCrossBuild := false // true if you cross-build the project for multiple Scala versions
releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  releaseStepCommand("publishSigned"),
  releaseStepCommand("sonatypeBundleRelease"),
  setNextVersion,
  commitNextVersion,
  pushChanges
)

