resolvers += "HMRC-open-artefacts-maven" at "https://open.artefacts.tax.service.gov.uk/maven2"

resolvers += Resolver.url(
  "HMRC-open-artefacts-ivy",
  url("https://open.artefacts.tax.service.gov.uk/ivy2")
)(Resolver.ivyStylePatterns)

addSbtPlugin("uk.gov.hmrc"               % "sbt-auto-build"     % "3.9.0")
addSbtPlugin("uk.gov.hmrc"               % "sbt-distributables" % "2.2.0")
addSbtPlugin("com.typesafe.play"         % "sbt-plugin"         % "2.8.20")
addSbtPlugin("org.scoverage"             % "sbt-scoverage"      % "2.0.8")
addSbtPlugin("org.irundaia.sbt"          % "sbt-sassify"        % "1.5.1")
addSbtPlugin("net.ground5hark.sbt"       % "sbt-concat"         % "0.2.0")
addSbtPlugin("com.typesafe.sbt"          % "sbt-digest"         % "1.1.4")
addSbtPlugin("com.timushev.sbt"          % "sbt-updates"        % "0.6.4")
addSbtPlugin("org.scalameta"             % "sbt-scalafmt"       % "2.5.0")
addSbtPlugin("ch.epfl.scala"             % "sbt-scalafix"       % "0.11.0")
addSbtPlugin("io.github.davidgregory084" % "sbt-tpolecat"       % "0.4.4")

ThisBuild / libraryDependencySchemes ++= Seq(
  "org.scala-lang.modules" %% "scala-xml" % VersionScheme.Always
)

addDependencyTreePlugin
