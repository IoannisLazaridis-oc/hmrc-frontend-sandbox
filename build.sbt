import play.sbt.routes.RoutesKeys
import scoverage.ScoverageKeys
import uk.gov.hmrc.DefaultBuildSettings
import scala.Ordering.Implicits._
import _root_.io.github.davidgregory084.ScalaVersion
import _root_.io.github.davidgregory084.DevMode

lazy val appName: String = "hmrc-frontend-sandbox"

lazy val root = Project(appName, file("."))
  .configs(IntegrationTest)
  .enablePlugins(PlayScala, SbtDistributablesPlugin)
  .disablePlugins(
    JUnitXmlReportPlugin
  ) // Required to prevent https://github.com/scalatest/scalatest/issues/1427
  .settings(
    inThisBuild(buildSettings),
    inConfig(Test)(testSettings),
    inConfig(IntegrationTest)(itSettings),
    inConfig(IntegrationTest)(ScalafmtPlugin.scalafmtConfigSettings),
    inConfig(IntegrationTest)(scalafixConfigSettings(IntegrationTest)),
    scalacSettings,
    scoverageSettings,
    playSettings,
    sbtWebSettings
  )
  .settings(
    majorVersion := 0,
    scalaVersion := "2.13.11",
    name         := appName,
    libraryDependencies ++= AppDependencies(),
    Global / onChangedBuildSource := ReloadOnSourceChanges,
    // Explicitly override the resolvers added by sbt-auto-build:
    // the private resolvers hang when you're not connected to HMRC's VPN
    resolvers := Seq(
      DefaultMavenRepository,
      MavenRepository(
        "HMRC-open-artefacts-maven2",
        "https://open.artefacts.tax.service.gov.uk/maven2"
      ),
      Resolver.url(
        "HMRC-open-artefacts-ivy2",
        url("https://open.artefacts.tax.service.gov.uk/ivy2")
      )(Resolver.ivyStylePatterns)
    )
  )

lazy val buildSettings = Def.settings(
  useSuperShell     := false,
  semanticdbEnabled := true,
  semanticdbVersion := scalafixSemanticdb.revision,
  scalafixDependencies ++= Seq(
    "com.github.liancheng" %% "organize-imports" % "0.6.0"
  )
)

lazy val scalacSettings = Def.settings(
  ThisBuild / tpolecatDefaultOptionsMode := DevMode,
  tpolecatScalacOptions ++= Set(
    // Fix patmat exhaustivity warnings caused by the scaffold code
    ScalacOptions.other(
      "-Ypatmat-exhaust-depth",
      List("40"),
      // -Ypatmat-exhaust-depth was removed in Scala 3
      version => version < ScalaVersion.V3_0_0
    ),
    // Silence warnings from generated code
    ScalacOptions.other(
      "-Wconf:src=target/.*:silent",
      version => {
        // -Wconf was added in Scala 2.13.2 and backported to Scala 2.12.13
        version.isBetween(ScalaVersion(2, 12, 13), ScalaVersion.V2_13_0) ||
        version.isBetween(ScalaVersion(2, 13, 2), ScalaVersion.V3_0_0)
      }
    )
  ),
  Test / tpolecatExcludeOptions ++= Set(
    ScalacOptions.warnDeadCode,        // Triggered by Mockito any()
    ScalacOptions.warnNonUnitStatement // Triggered by ScalaTest assertions
  ),
  IntegrationTest / tpolecatExcludeOptions ++= Set(
    ScalacOptions.warnNonUnitStatement // Triggered by ScalaTest assertions
  )
)

lazy val testSettings = Def.settings(
  fork := true,
  javaOptions ++= Seq(
    "-Dconfig.resource=test.application.conf"
  )
)

lazy val itSettings = Defaults.itSettings ++ Def.settings(
  fork              := true,
  parallelExecution := false,
  unmanagedSourceDirectories := Seq(
    baseDirectory.value / "it",
    baseDirectory.value / "test" / "generators"
  ),
  unmanagedResourceDirectories := Seq(
    baseDirectory.value / "it" / "resources"
  ),
  javaOptions ++= Seq(
    "-Dconfig.resource=it.application.conf"
  )
)

lazy val scoverageSettings = Def.settings(
  Test / parallelExecution               := false,
  ScoverageKeys.coverageMinimumStmtTotal := 88,
  ScoverageKeys.coverageFailOnMinimum    := true,
  ScoverageKeys.coverageHighlighting     := true,
  ScoverageKeys.coverageExcludedFiles := Seq(
    "<empty>",
    "Reverse.*",
    ".*Routes.*",
    ".*handlers.*",
    ".*components.*",
    ".*viewmodels.govuk.*"
  ).mkString(";")
)

lazy val playSettings = Def.settings(
  PlayKeys.playDefaultPort := 9335,
  RoutesKeys.routesImport ++= Seq(
    "models._",
    "uk.gov.hmrc.play.bootstrap.binders.RedirectUrl"
  ),
  TwirlKeys.templateImports ++= Seq(
    "play.twirl.api.HtmlFormat",
    "play.twirl.api.HtmlFormat._",
    "views.ViewUtils._",
    "models.Mode",
    "controllers.routes._",
    "viewmodels.govuk.all._",
    "uk.gov.hmrc.govukfrontend.views.html.components._",
    "uk.gov.hmrc.hmrcfrontend.views.html.components._",
    "uk.gov.hmrc.hmrcfrontend.views.html.helpers._"
  )
)

lazy val sbtWebSettings = Def.settings(
  // prevent removal of unused code which generates warning errors due to use of third-party libs
  pipelineStages := Seq(digest),
  // below line required to force asset pipeline to operate in dev rather than only prod
  Assets / pipelineStages := Seq(concat),
  // concatenate js
  Concat.groups := Seq(
    "javascripts/application.js" ->
      group(
        Seq(
          "javascripts/app.js"
        )
      )
  )
)

ThisBuild / libraryDependencySchemes ++= Seq(
  "org.scala-lang.modules" %% "scala-xml" % VersionScheme.Always
)
