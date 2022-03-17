#!/usr/bin/env bash
sbt clean scalafmtCheckAll "scalafixAll --check" coverage test IntegrationTest/test coverageOff coverageReport
