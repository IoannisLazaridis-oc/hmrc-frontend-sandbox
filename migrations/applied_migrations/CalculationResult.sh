#!/bin/bash

echo ""
echo "Applying migration CalculationResult"

echo "Adding routes to conf/app.routes"
echo "" >> ../conf/app.routes
echo "GET        /calculationResult                       controllers.CalculationResultController.onPageLoad()" >> ../conf/app.routes

echo "Adding messages to conf.messages"
echo "" >> ../conf/messages.en
echo "calculationResult.title = calculationResult" >> ../conf/messages.en
echo "calculationResult.heading = calculationResult" >> ../conf/messages.en

echo "Migration CalculationResult completed"
