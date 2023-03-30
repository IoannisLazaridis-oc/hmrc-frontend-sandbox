import play.sbt.routes.RoutesKeys
import scoverage.ScoverageKeys
import uk.gov.hmrc.DefaultBuildSettings

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
    scalaVersion := "2.13.10",
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
  // Silence warnings from generated code
  scalacOptions ++= Seq(
    "-Wconf:src=target/.*:silent",
    "-Ypatmat-exhaust-depth",
    "40"
  ),
  scalacOptions ~= { opts =>
    opts.filterNot(Set("-Werror", "-Xfatal-warnings"))
  },
  Test / scalacOptions ~= { opts =>
    opts.filterNot(
      Set(
        "-Wdead-code",    // Triggered by Mockito's any()
        "-Wvalue-discard" // Triggered by normal use of Scalatest
      )
    )
  }
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
