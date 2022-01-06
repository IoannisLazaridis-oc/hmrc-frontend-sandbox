#!/bin/bash

echo ""
echo "Applying migration FirstNumber"

echo "Adding routes to conf/app.routes"

echo "" >> ../conf/app.routes
echo "GET        /firstNumber                  controllers.FirstNumberController.onPageLoad(mode: Mode = NormalMode)" >> ../conf/app.routes
echo "POST       /firstNumber                  controllers.FirstNumberController.onSubmit(mode: Mode = NormalMode)" >> ../conf/app.routes

echo "GET        /changeFirstNumber                        controllers.FirstNumberController.onPageLoad(mode: Mode = CheckMode)" >> ../conf/app.routes
echo "POST       /changeFirstNumber                        controllers.FirstNumberController.onSubmit(mode: Mode = CheckMode)" >> ../conf/app.routes

echo "Adding messages to conf.messages"
echo "" >> ../conf/messages.en
echo "firstNumber.title = FirstNumber" >> ../conf/messages.en
echo "firstNumber.heading = FirstNumber" >> ../conf/messages.en
echo "firstNumber.checkYourAnswersLabel = FirstNumber" >> ../conf/messages.en
echo "firstNumber.error.nonNumeric = Enter your firstNumber using numbers" >> ../conf/messages.en
echo "firstNumber.error.required = Enter your firstNumber" >> ../conf/messages.en
echo "firstNumber.error.wholeNumber = Enter your firstNumber using whole numbers" >> ../conf/messages.en
echo "firstNumber.error.outOfRange = FirstNumber must be between {0} and {1}" >> ../conf/messages.en
echo "firstNumber.change.hidden = FirstNumber" >> ../conf/messages.en

echo "Adding to UserAnswersEntryGenerators"
awk '/trait UserAnswersEntryGenerators/ {\
    print;\
    print "";\
    print "  implicit lazy val arbitraryFirstNumberUserAnswersEntry: Arbitrary[(FirstNumberPage.type, JsValue)] =";\
    print "    Arbitrary {";\
    print "      for {";\
    print "        page  <- arbitrary[FirstNumberPage.type]";\
    print "        value <- arbitrary[Int].map(Json.toJson(_))";\
    print "      } yield (page, value)";\
    print "    }";\
    next }1' ../test/generators/UserAnswersEntryGenerators.scala > tmp && mv tmp ../test/generators/UserAnswersEntryGenerators.scala

echo "Adding to PageGenerators"
awk '/trait PageGenerators/ {\
    print;\
    print "";\
    print "  implicit lazy val arbitraryFirstNumberPage: Arbitrary[FirstNumberPage.type] =";\
    print "    Arbitrary(FirstNumberPage)";\
    next }1' ../test/generators/PageGenerators.scala > tmp && mv tmp ../test/generators/PageGenerators.scala

echo "Adding to UserAnswersGenerator"
awk '/val generators/ {\
    print;\
    print "    arbitrary[(FirstNumberPage.type, JsValue)] ::";\
    next }1' ../test/generators/UserAnswersGenerator.scala > tmp && mv tmp ../test/generators/UserAnswersGenerator.scala

echo "Migration FirstNumber completed"
