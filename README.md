# HMRC Frontend Sandbox

Welcome to the HMRC Frontend Sandbox, this application contains all the required foundations and boiler plate for any HMRC frontend application. It has been created using `scaffolds`, an internally developed [templating solution](https://github.com/hmrc/hmrc-frontend-scaffold.g8) that enables Scrum Teams to quickly set up all the required boiler plate to create a new web frontend so that teams can focus on the actual business problem they need to solve.

This application follows a standard [`MVC pattern`](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller), with its main components located here:

- [Models] (https://github.com/opencastsoftware/hmrc-frontend-sandbox/tree/master/app/models)
- [Views] (https://github.com/opencastsoftware/hmrc-frontend-sandbox/tree/master/app/views)
- [Controllers] (https://github.com/opencastsoftware/hmrc-frontend-sandbox/tree/master/app/controllers)

## Routes
You can see the catalogue of accessible pages on your site via your [`routes`](./conf/app.routes) file. Every new page you create will require its associated `route` here.

## Navigation
Navigation behaviour is abstracted out from the `controller` and injected into it as an external dependency. Typically, the [`navigator`](./app/navigation/Navigator.scala) exposes a `nextPage` method that will encapsulate all behaviour related for navigation purposes.

## Navigation modes
There are two modes of navigation, `NormalMode` and `CheckMode`. Typically, you will use `NormalMode` for navigation that will progress you through your user journey (i.e. go forward, default navigation) and `CheckMode` for navigation that will go back into a specific page to modify an input. Have a look at the `navigator` tests [here](./test/navigation/NavigatorSpec.scala) to get a deeper understanding on how it works.

## Check your answers pages
These pages will typically appear at the end of a given section within your journey and it will display all the answers given for the section. It offers the user the possibility of changing any of the answers given (have a look at the navigation [here](./app/controllers/CheckYourAnswersController.scala#L48), note that it is done in `CheckMode`)

## What are controller "Actions"?

For each of the methods within a `controller` we can run actions that will be executed before the method body is, the most basic example can be found within the [Index Controller](https://github.com/opencastsoftware/hmrc-frontend-sandbox/blob/master/app/controllers/IndexController.scala#L32), you can see that we run an `identify` action (ie, ensure the user is authenticated) before returning the [Index View](https://github.com/opencastsoftware/hmrc-frontend-sandbox/blob/master/app/views/IndexView.scala.html). 
`Action` is an interface that Play exposes that allows us to encapsulate common behaviour. They are similar in concept to Scala's `Function` type and we chain Actions together using the `andThen` method.

## How can I add authentication?

It is worth noting that `authentication actions` have been stubbed as we have no way of reaching HMRC's auth service. You can see the stubbed `IdentifierAction` [here](https://github.com/opencastsoftware/hmrc-frontend-sandbox/blob/master/app/controllers/actions/IdentifierAction.scala#L45). Notice that all related tests in [`AuthActionSpec`](https://github.com/opencastsoftware/hmrc-frontend-sandbox/blob/master/test/controllers/actions/AuthActionSpec.scala) are ignored since the behaviour described in them is commented out.

## Where are the tests?

The application has been `test driven` and for every file containing production code, there should be an associated `Spec` file containing all its tests. You can see all the tests [here](./test).

You can run all tests by running `sbt test`. Alternatively, if you load `sbt` and once it is loaded you run `~testQuick` it will monitor your files and run its associated tests every time you make a change in any file. This functionality is very handy when test driving your application.

## Content and Languages

The application supports both English and Welsh. All content displayed within any views will always be fed through a `messages file` located [here (for English language)](https://github.com/opencastsoftware/hmrc-frontend-sandbox/blob/master/conf/messages.en) and [here (for Welsh language)](https://github.com/opencastsoftware/hmrc-frontend-sandbox/blob/master/conf/messages.cy)

The switch of languages is executed via the Play! `LanguageController`. You can see where this code is executed [here](./app/views/templates/Layout.scala.html#L66)


## Persistance

You will need a local instance of mongoDB running locally in port `27017`. If you have never installed mongoDB, you can easily do it by following [this guide](https://docs.mongodb.com/manual/tutorial/install-mongodb-on-os-x/) (it is to have mongoDB as a service running in the background).

Once you have mongoDB installed, you can install a mongoDB GUI to be able to see and manage the data generated by your sandbox application. [Robo 3T](https://robomongo.org/) is a good GUI and very simple to use