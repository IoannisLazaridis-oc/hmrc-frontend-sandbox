#!/usr/bin/env bash
sbt clean headerCheck scalafmtCheckAll scalafmtSbtCheck "scalafixAll --check" coverage test IntegrationTest/test coverageOff coverageReport
