import ReleaseTransformations.*
import sbtversionpolicy.withsbtrelease.ReleaseVersion

name := "use-newer-java"

description := "A small lib to give your users a friendly message that they're using a version of Java that's too old."

libraryDependencies ++= Seq(
  "junit" % "junit" % "4.13.2" % Test,
  "com.novocode" % "junit-interface" % "0.11" % Test
)

Compile / compile / javacOptions ++= Seq("-source", "1.5", "-target", "1.5")

organization := "com.madgag"
licenses := Seq(License.Apache2)

releaseVersion := ReleaseVersion.fromAggregatedAssessedCompatibilityWithLatestRelease().value
releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  setNextVersion,
  commitNextVersion
)
