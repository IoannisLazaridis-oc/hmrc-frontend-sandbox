import sbt._

object AppDependencies {
  private val bootstrapVersion = "7.8.0"
  private val mongoVersion     = "1.3.0"

  private val compile = Seq(
    play.sbt.PlayImport.ws,
    "uk.gov.hmrc"       %% "play-frontend-hmrc"         % s"7.15.0-play-28",
    "uk.gov.hmrc"       %% "bootstrap-frontend-play-28" % "7.19.0",
    "uk.gov.hmrc.mongo" %% "hmrc-mongo-play-28"         % mongoVersion
  )

  private val test = Seq(
    "uk.gov.hmrc"         %% s"bootstrap-test-play-28"  % "7.19.0",
    "org.mockito"         %% "mockito-scala-scalatest"  % "1.17.14",
    "org.scalatestplus"   %% "scalacheck-1-16"          % "3.2.14.0",
    "com.vladsch.flexmark" % "flexmark-all"             % "0.64.8",
    "uk.gov.hmrc.mongo"   %% s"hmrc-mongo-test-play-28" % mongoVersion
  ).map(_ % "test, it")

  def apply(): Seq[ModuleID] = compile ++ test
}
