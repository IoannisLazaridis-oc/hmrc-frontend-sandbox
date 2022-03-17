import sbt._

object AppDependencies {
  import play.core.PlayVersion

  private val compile = Seq(
    play.sbt.PlayImport.ws,
    "uk.gov.hmrc"       %% "play-frontend-hmrc"             % "0.58.0-play-28",
    "uk.gov.hmrc"       %% "play-conditional-form-mapping"  % "1.6.0-play-27",
    "uk.gov.hmrc"       %% "bootstrap-frontend-play-28"     % "4.2.0",
    "uk.gov.hmrc"       %% "play-language"                  % "4.12.0-play-28",
    "uk.gov.hmrc.mongo" %% "hmrc-mongo-play-27"             % "0.49.0",
    "com.google.inject" % "guice"                           % "5.0.1"
  )

  private val test = Seq(
    "org.scalatest"           %% "scalatest"               % "3.2.7",
    "org.scalatestplus"       %% "scalacheck-1-15"         % "3.2.7.0",
    "org.scalatestplus"       %% "mockito-3-4"             % "3.2.7.0",
    "org.scalatestplus.play"  %% "scalatestplus-play"      % "5.1.0",
    "org.pegdown"             %  "pegdown"                 % "1.6.0",
    "org.jsoup"               %  "jsoup"                   % "1.13.1",
    "com.typesafe.play"       %% "play-test"               % PlayVersion.current,
    "org.mockito"             %% "mockito-scala"           % "1.16.0",
    "org.scalacheck"          %% "scalacheck"              % "1.15.3",
    "uk.gov.hmrc.mongo"       %% "hmrc-mongo-test-play-27" % "0.49.0",
  ).map(_ % "test, it")

  def apply(): Seq[ModuleID] = (compile ++ test).map(_.withSources())
}
