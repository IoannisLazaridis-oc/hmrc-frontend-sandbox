/*
 * Copyright 2023 HM Revenue & Customs
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
import uk.gov.hmrc.govukfrontend.views.Aliases.Value
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.Text
import uk.gov.hmrc.govukfrontend.views.viewmodels.summarylist.{
  ActionItem,
  Actions,
  Key,
  SummaryListRow
}
import viewmodels.govuk.SummaryListFluency
import views.html.CheckYourAnswersView

class CheckYourAnswersControllerSpec extends SpecBase with SummaryListFluency {

  "Check Your Answers Controller" - {

    "must render all records" in {

      val userAnswers = emptyUserAnswers
        .set(FirstNumberPage, 5)
        .success
        .value
        .set(SecondNumberPage, 10)
        .success
        .value

      val application = applicationBuilder(userAnswers = Some(userAnswers)).build()

      running(application) {
        val request = FakeRequest(GET, routes.CheckYourAnswersController.onPageLoad.url)

        val result = route(application, request).value

        val view = application.injector.instanceOf[CheckYourAnswersView]
        val expectedRow1 = SummaryListRow(
          key = Key(
            content = Text("First number"),
            classes = "govuk-!-width-two-thirds"
          ),
          value = Value(
            content = Text("5"),
            classes = "govuk-!-width-one-quater"
          ),
          actions = Some(
            Actions(
              items = Seq(
                ActionItem(
                  href = "/hmrc-frontend-sandbox/change-first-number",
                  content = Text("Change"),
                  visuallyHiddenText = None
                )
              )
            )
          )
        )

        val expectedRow2 = SummaryListRow(
          key = Key(
            content = Text("Second number"),
            classes = "govuk-!-width-two-thirds"
          ),
          value = Value(
            content = Text("10"),
            classes = "govuk-!-width-one-quater"
          ),
          actions = Some(
            Actions(
              items = Seq(
                ActionItem(
                  href = "/hmrc-frontend-sandbox/change-second-number",
                  content = Text("Change"),
                  visuallyHiddenText = None
                )
              )
            )
          )
        )

        val list = SummaryListViewModel(Seq(expectedRow1, expectedRow2))

        status(result) mustEqual OK
        contentAsString(result) mustEqual view(list)(request, messages(application)).toString
      }
    }

    "must redirect to Session Expired for a GET if no existing data is found" in {

      val application = applicationBuilder(userAnswers = None).build()

      running(application) {
        val request = FakeRequest(GET, routes.CheckYourAnswersController.onPageLoad.url)

        val result = route(application, request).value

        status(result) mustEqual SEE_OTHER
        redirectLocation(result).value mustEqual routes.JourneyRecoveryController.onPageLoad().url
      }
    }
  }
}
