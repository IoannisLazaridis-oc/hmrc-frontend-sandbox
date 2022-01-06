#!/bin/bash

echo ""
echo "Applying migration SecondNumber"

echo "Adding routes to conf/app.routes"

echo "" >> ../conf/app.routes
echo "GET        /secondNumber                  controllers.SecondNumberController.onPageLoad(mode: Mode = NormalMode)" >> ../conf/app.routes
echo "POST       /secondNumber                  controllers.SecondNumberController.onSubmit(mode: Mode = NormalMode)" >> ../conf/app.routes

echo "GET        /changeSecondNumber                        controllers.SecondNumberController.onPageLoad(mode: Mode = CheckMode)" >> ../conf/app.routes
echo "POST       /changeSecondNumber                        controllers.SecondNumberController.onSubmit(mode: Mode = CheckMode)" >> ../conf/app.routes

echo "Adding messages to conf.messages"
echo "" >> ../conf/messages.en
echo "secondNumber.title = SecondNumber" >> ../conf/messages.en
echo "secondNumber.heading = SecondNumber" >> ../conf/messages.en
echo "secondNumber.checkYourAnswersLabel = SecondNumber" >> ../conf/messages.en
echo "secondNumber.error.nonNumeric = Enter your secondNumber using numbers" >> ../conf/messages.en
echo "secondNumber.error.required = Enter your secondNumber" >> ../conf/messages.en
echo "secondNumber.error.wholeNumber = Enter your secondNumber using whole numbers" >> ../conf/messages.en
echo "secondNumber.error.outOfRange = SecondNumber must be between {0} and {1}" >> ../conf/messages.en
echo "secondNumber.change.hidden = SecondNumber" >> ../conf/messages.en

echo "Adding to UserAnswersEntryGenerators"
awk '/trait UserAnswersEntryGenerators/ {\
    print;\
    print "";\
    print "  implicit lazy val arbitrarySecondNumberUserAnswersEntry: Arbitrary[(SecondNumberPage.type, JsValue)] =";\
    print "    Arbitrary {";\
    print "      for {";\
    print "        page  <- arbitrary[SecondNumberPage.type]";\
    print "        value <- arbitrary[Int].map(Json.toJson(_))";\
    print "      } yield (page, value)";\
    print "    }";\
    next }1' ../test/generators/UserAnswersEntryGenerators.scala > tmp && mv tmp ../test/generators/UserAnswersEntryGenerators.scala

echo "Adding to PageGenerators"
awk '/trait PageGenerators/ {\
    print;\
    print "";\
    print "  implicit lazy val arbitrarySecondNumberPage: Arbitrary[SecondNumberPage.type] =";\
    print "    Arbitrary(SecondNumberPage)";\
    next }1' ../test/generators/PageGenerators.scala > tmp && mv tmp ../test/generators/PageGenerators.scala

echo "Adding to UserAnswersGenerator"
awk '/val generators/ {\
    print;\
    print "    arbitrary[(SecondNumberPage.type, JsValue)] ::";\
    next }1' ../test/generators/UserAnswersGenerator.scala > tmp && mv tmp ../test/generators/UserAnswersGenerator.scala

echo "Migration SecondNumber completed"
