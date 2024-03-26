package controllers

import base.SpecBase
import play.api.test.FakeRequest
import play.api.test.Helpers._
class ProfileControllerSpec extends SpecBase {
  "Profile Controller" must {
    "return OK and the correct view for a GET" in {
      val application = applicationBuilder(userAnswers = Some(emptyUserAnswers)).build()

      running(application) {
        val request = FakeRequest(GET, routes.ProfileController.onPageLoad().url)
        val result = route(application, request).value

        status(result) mustEqual OK
      }
    }
  }
}
