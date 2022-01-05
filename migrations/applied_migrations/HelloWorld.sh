#!/bin/bash

echo ""
echo "Applying migration HelloWorld"

echo "Adding routes to conf/app.routes"
echo "" >> ../conf/app.routes
echo "GET        /helloWorld                       controllers.HelloWorldController.onPageLoad()" >> ../conf/app.routes

echo "Adding messages to conf.messages"
echo "" >> ../conf/messages.en
echo "helloWorld.title = helloWorld" >> ../conf/messages.en
echo "helloWorld.heading = helloWorld" >> ../conf/messages.en

echo "Migration HelloWorld completed"
