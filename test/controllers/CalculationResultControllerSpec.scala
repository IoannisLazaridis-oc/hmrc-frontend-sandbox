/*
 * Copyright 2022 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package controllers

import base.SpecBase
import pages.{FirstNumberPage, SecondNumberPage}
import play.api.test.FakeRequest
import play.api.test.Helpers._
import views.html.CalculationResultView

class CalculationResultControllerSpec extends SpecBase {

  "CalculationResult Controller" - {

    "must return 0 if there is no numbers" in {

      val application = applicationBuilder(userAnswers = Some(emptyUserAnswers)).build()

      running(application) {
        val request = FakeRequest(GET, routes.CalculationResultController.onPageLoad().url)

        val result = route(application, request).value

        val view = application.injector.instanceOf[CalculationResultView]

        status(result) mustEqual OK
        contentAsString(result) mustEqual view(None)(request, messages(application)).toString
      }
    }

    "must return the sum of first number and second number" in {
      val userAnswers = emptyUserAnswers.set(FirstNumberPage,5).success.value
        .set(SecondNumberPage,10).success.value

      val application = applicationBuilder(userAnswers = Some(userAnswers)).build()

      running(application) {
        val request = FakeRequest(GET, routes.CalculationResultController.onPageLoad().url)

        val result = route(application, request).value

        val view = application.injector.instanceOf[CalculationResultView]

        status(result) mustEqual OK
        contentAsString(result) mustEqual view(Some(15))(request, messages(application)).toString
      }
    }

  }
}
