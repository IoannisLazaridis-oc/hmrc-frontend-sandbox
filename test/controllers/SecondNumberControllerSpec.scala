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
import forms.SecondNumberFormProvider
import models.{NormalMode, UserAnswers}
import navigation.{FakeNavigator, Navigator}
import org.mockito.ArgumentMatchers.any
import org.mockito.MockitoSugar
import pages.SecondNumberPage
import play.api.inject.bind
import play.api.mvc.Call
import play.api.test.FakeRequest
import play.api.test.Helpers._
import repositories.SessionRepository
import views.html.SecondNumberView

import scala.concurrent.Future

class SecondNumberControllerSpec extends SpecBase with MockitoSugar {

  val formProvider = new SecondNumberFormProvider()
  val form         = formProvider()

  def onwardRoute = Call("GET", "/foo")

  val validAnswer = 10

  lazy val secondNumberRoute = routes.SecondNumberController.onPageLoad(NormalMode).url

  "SecondNumber Controller" - {

    "must return OK and the correct view for a GET" in {

      val application = applicationBuilder(userAnswers = Some(emptyUserAnswers)).build()

      running(application) {
        val request = FakeRequest(GET, secondNumberRoute)

        val result = route(application, request).value

        val view = application.injector.instanceOf[SecondNumberView]

        status(result) mustEqual OK
        contentAsString(result) mustEqual view(form, NormalMode)(
          request,
          messages(application)
        ).toString
      }
    }

    "must populate the view correctly on a GET when the question has previously been answered" in {

      val userAnswers = UserAnswers(userAnswersId).set(SecondNumberPage, validAnswer).success.value

      val application = applicationBuilder(userAnswers = Some(userAnswers)).build()

      running(application) {
        val request = FakeRequest(GET, secondNumberRoute)

        val view = application.injector.instanceOf[SecondNumberView]

        val result = route(application, request).value

        status(result) mustEqual OK
        contentAsString(result) mustEqual view(form.fill(validAnswer), NormalMode)(
          request,
          messages(application)
        ).toString
      }
    }

    "must redirect to the next page when valid data is submitted" in {

      val mockSessionRepository = mock[SessionRepository]

      when(mockSessionRepository.set(any())) thenReturn Future.successful(true)

      val application =
        applicationBuilder(userAnswers = Some(emptyUserAnswers))
          .overrides(
            bind[Navigator].toInstance(new FakeNavigator(onwardRoute)),
            bind[SessionRepository].toInstance(mockSessionRepository)
          )
          .build()

      running(application) {
        val request =
          FakeRequest(POST, secondNumberRoute)
            .withFormUrlEncodedBody(("value", validAnswer.toString))

        val result = route(application, request).value

        status(result) mustEqual SEE_OTHER
        redirectLocation(result).value mustEqual onwardRoute.url
      }
    }

    "must return a Bad Request and errors when invalid data is submitted" in {

      val application = applicationBuilder(userAnswers = Some(emptyUserAnswers)).build()

      running(application) {
        val request =
          FakeRequest(POST, secondNumberRoute)
            .withFormUrlEncodedBody(("value", "invalid value"))

        val boundForm = form.bind(Map("value" -> "invalid value"))

        val view = application.injector.instanceOf[SecondNumberView]

        val result = route(application, request).value

        status(result) mustEqual BAD_REQUEST
        contentAsString(result) mustEqual view(boundForm, NormalMode)(
          request,
          messages(application)
        ).toString
      }
    }

    "must redirect to Session Expired for a GET if no existing data is found" in {

      val application = applicationBuilder(userAnswers = None).build()

      running(application) {
        val request = FakeRequest(GET, secondNumberRoute)

        val result = route(application, request).value

        status(result) mustEqual SEE_OTHER
        redirectLocation(result).value mustEqual routes.JourneyRecoveryController.onPageLoad().url
      }
    }

    "must redirect to Session Expired for a POST if no existing data is found" in {

      val application = applicationBuilder(userAnswers = None).build()

      running(application) {
        val request =
          FakeRequest(POST, secondNumberRoute)
            .withFormUrlEncodedBody(("value", validAnswer.toString))

        val result = route(application, request).value

        status(result) mustEqual SEE_OTHER

        redirectLocation(result).value mustEqual routes.JourneyRecoveryController.onPageLoad().url
      }
    }

    "must return a Bad Request and errors when out of range data is submitted" in {

      val application = applicationBuilder(userAnswers = Some(emptyUserAnswers)).build()

      running(application) {
        val request =
          FakeRequest(POST, secondNumberRoute)
            .withFormUrlEncodedBody(("value", "99"))

        val boundForm = form.bind(Map("value" -> "99"))

        val view = application.injector.instanceOf[SecondNumberView]

        val result         = route(application, request).value
        val resultAsString = contentAsString(result)

        status(result) mustEqual BAD_REQUEST
        resultAsString mustEqual view(boundForm, NormalMode)(
          request,
          messages(application)
        ).toString

        // Ensure the numbers in the range are populated and not placeholders {0} {1}
        resultAsString.contains("Your second number must be between 10 and 20") mustBe true

      }
    }

    "must return a Bad Request and errors when out of range data is submitted in Welsh" in {

      val application = applicationBuilder(userAnswers = Some(emptyUserAnswers)).build()

      running(application) {
        val request =
          FakeRequest(POST, secondNumberRoute)
            .withFormUrlEncodedBody(("value", "99"))
            .withHeaders(("Accept-Language", "cy"))

        val boundForm = form.bind(Map("value" -> "99"))

        val view = application.injector.instanceOf[SecondNumberView]

        val result         = route(application, request).value
        val resultAsString = contentAsString(result)

        status(result) mustEqual BAD_REQUEST
        resultAsString mustEqual view(boundForm, NormalMode)(
          request,
          messagesWelsh(application)
        ).toString

        // Ensure the numbers in the range are populated and not placeholders {0} {1}
        // Previously the Welsh message was not doing this
        resultAsString.contains("Rhaid i&#x27;ch ail rif fod rhwng 10 a 20") mustBe true

      }
    }

  }
}
