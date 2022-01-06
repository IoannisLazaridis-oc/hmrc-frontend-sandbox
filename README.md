# HMRC Frontend Sandbox

Welcome to the HMRC Frontend Sandbox, this application contains all the required foundations and boiler plate for any HMRC frontend application. It has been created using `scaffolds`, an internally developed [templating solution](https://github.com/hmrc/hmrc-frontend-scaffold.g8) that enables Scrum Teams to quickly set up all the required boiler plate to create a new web frontend so that teams can focus on the actual business problem they need to solve.

This application follows a standard [`MVC pattern`](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller), with its main components located here:

- [Models] (https://github.com/opencastsoftware/hmrc-frontend-sandbox/tree/master/app/models)
- [Views] (https://github.com/opencastsoftware/hmrc-frontend-sandbox/tree/master/app/views)
- [Controllers] (https://github.com/opencastsoftware/hmrc-frontend-sandbox/tree/master/app/controllers)


## How do I add content?
All content displayed within any views will always be fed through a `messages file` located [here (for English language)](https://github.com/opencastsoftware/hmrc-frontend-sandbox/blob/master/conf/messages.en) and [here (for Welsh language)](https://github.com/opencastsoftware/hmrc-frontend-sandbox/blob/master/conf/messages.cy)

## What are controller "Actions"?
For each of the methods within a `controller` we can run actions that will be executed before the method body is, the most basic example can be found within the [Index Controller](https://github.com/opencastsoftware/hmrc-frontend-sandbox/blob/master/app/controllers/IndexController.scala#L32), you can see that we run an `identify` action (ie, ensure the user is authenticated) before returning the [Index View](https://github.com/opencastsoftware/hmrc-frontend-sandbox/blob/master/app/views/IndexView.scala.html).

`Action` is an interface that Play exposes that allows us to encapsulate common behaviour. They are similar in concept to Scala's `Function` type and we chain Actions together using the `andThen` method.

## How can I add authentication?
It is worth noting that `authentication actions` have been stubbed as we have no way of reaching HMRC's auth service. You can see the stubbed `IdentifierAction` [here](https://github.com/opencastsoftware/hmrc-frontend-sandbox/blob/master/app/controllers/actions/IdentifierAction.scala#L45).

## Where are the tests?

The application has been `test driven` and for every file containing production code, there should be an associated `Spec` file containing all its tests. You can see all the tests [here](./test).

