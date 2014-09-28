organization := "me.lessis"

name := "lapse"

version := "0.1.0"

description := "Measures the passage of time"

crossScalaVersions := Seq("2.10.4", "2.11.2")

scalaVersion := crossScalaVersions.value.head

licenses := Seq(
  ("MIT", url(s"https://github.com/softprops/${name.value}/blob/${version.value}/LICENSE")))

homepage := Some(url(s"https://github.com/softprops/${name.value}/#readme"))

seq(bintraySettings:_*)

bintray.Keys.packageLabels in bintray.Keys.bintray := Seq("measurements", "instrumenting")

seq(lsSettings:_*)

LsKeys.tags in LsKeys.lsync := (bintray.Keys.packageLabels in bintray.Keys.bintray).value

externalResolvers in LsKeys.lsync := (resolvers in bintray.Keys.bintray).value

pomExtra := (
  <scm>
    <url>git@github.com:softprops/lapse.git</url>
    <connection>scm:git:git@github.com:softprops/lapse.git</connection>
  </scm>
  <developers>
    <developer>
      <id>softprops</id>
      <name>Doug Tangren</name>
      <url>https://github.com/softprops</url>
    </developer>
  </developers>)
