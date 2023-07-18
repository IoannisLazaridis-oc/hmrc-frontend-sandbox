# HMRC Frontend Sandbox

Welcome to the HMRC Frontend Sandbox, this application contains all the required foundations and boiler plate for any HMRC frontend application. It has been created using `scaffolds`, an internally developed [templating solution](https://github.com/hmrc/hmrc-frontend-scaffold.g8) that enables Scrum Teams to quickly set up all the required boiler plate to create a new web frontend so that teams can focus on the actual business problem they need to solve.

This application follows a standard [`MVC pattern`](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller), with its main components located here:

- [Models](https://github.com/opencastsoftware/hmrc-frontend-sandbox/tree/master/app/models)
- [Views](https://github.com/opencastsoftware/hmrc-frontend-sandbox/tree/master/app/views)
- [Controllers](https://github.com/opencastsoftware/hmrc-frontend-sandbox/tree/master/app/controllers)

It is worth noting that this application utilises the `Play! framework`, a web application framework for languages compiled and run on the JVM (mainly Java and Scala). We recommend that you bookmark [this documentation](https://www.playframework.com/documentation/2.1.x/ScalaHome) and use it as the `go to` place to have a better understanding on how this application works. There are also some really handy tutorials [here](https://www.playframework.com/documentation/2.1.x/ScalaTodoList) if you want to understand `Play!` better.

## How to use this sandbox

This application replicates the basics of the majority of web application front ends created by HMRC. Take your time with it, run it, play around and experiment with it, understand how everything works - get familiarised with the patterns, styling and terminology and reach out if you have any questions. If you get familiar with this sandbox, then everything will be a little bit more familiar when you start at HMRC.

Once you are reasonably happy with it, then why don't you take the next step and try and implement [this exercise](./exercises/energySourceChecker.md)?

## How to run it

### Java SDK

You will need the Java JDK installed, we recommend you manage this with an SDK Manager like [SDKMan](https://sdkman.io/). Install SDKMan (instructions [here](https://sdkman.io/install)) and once installed, you will be able to install java SDK through it (instructions [here](https://sdkman.io/usage)).

After that, you will be able to select which version of JDK you want to use (for example, 1.8). Remember you will have to point your `JAVA_HOME` to use `SDKMan`, for example:

```
export JAVA_HOME="/Users/yourUserName/.sdkman/candidates/java/current"
```

You should be able to see that all is working with your expected version by running the command `java -version`

```
java -version
openjdk version "1.8.0_292"
OpenJDK Runtime Environment (build 1.8.0_292-b10)
Eclipse OpenJ9 VM (build openj9-0.26.0, JRE 1.8.0 Mac OS X amd64-64-Bit Compressed References 20210421_909 (JIT enabled, AOT enabled)
OpenJ9   - b4cc246d9
OMR      - 162e6f729
JCL      - 2a5e268814 based on jdk8u292-b10)
```

### MongoDB Setup
To run this project, you will need a local instance of MongoDB running on your machine. Follow the steps below to set it
up:

1. Install MongoDB:
    - Open Terminal and run the following command to install MongoDB using Homebrew:
   ```shell
    brew install mongodb-community@6.0
   ```
    - If you don't have Homebrew installed, follow the instructions [here](https://brew.sh/) to install it first.

2. Start MongoDB as a service:
   - Open Terminal and run the following command:
   ```shell
   brew services start mongodb-community@6.0
   ```
   - This command will start MongoDB as a background service. You can use
   the ```brew services stop mongodb-community@6.0``` command to stop the service when needed.


3. Verify MongoDB service installation:
    - Open a new Terminal or command prompt window.
    - Run the following command to start the MongoDB shell:
      ```shell
      brew services list
      ```
    - If MongoDB is installed and running correctly, you should see the MongoDB service.
      ```shell
      mongodb-community started robert.buczek ~/Library/LaunchAgents/homebrew.mxcl.mongodb-community.plist
      ```

4. Verify MongoDB installation:
    - Open a new Terminal or command prompt window.
    - Run the following command to start the MongoDB shell:
      ```shell
      mongosh
      ```
    - If MongoDB is installed and running correctly, you should see the MongoDB wellcome message:
      ```shell
      Current Mongosh Log ID:	64b53b472b2c12e86ad5a4c0
      Connecting to:		mongodb://127.0.0.1:27017/?directConnection=true&serverSelectionTimeoutMS=2000&appName=mongosh+1.10.1
      Using MongoDB:		6.0.6
      Using Mongosh:		1.10.1

      For mongosh info see: https://docs.mongodb.com/mongodb-shell/


      To help improve our products, anonymous usage data is collected and sent to MongoDB periodically (https://www.mongodb.com/legal/privacy-policy).
      You can opt-out by running the disableTelemetry() command.

      ------
         The server generated these startup warnings when booting
         2023-07-17T13:49:15.343+01:00: Access control is not enabled for the database. Read and write access to data and configuration is unrestricted
      ------

      test>
      ```
  - Type `exit` to exit the MongoDB shell.
5. Configure MongoDB connection:
    - By default, MongoDB listens on port `27017`, which should already be set up in the application's configuration.
    - Make sure your MongoDB instance is running on the default port (`27017`). If you need to use a different port,
      update the application's configuration accordingly.

### MongoDB Setup (Docker)
1. Install Docker: Ensure that you have Docker installed on your machine. You can download and install Docker from the official Docker website (https://www.docker.com/get-started).
2. Pull the MongoDB Docker Image: Open a terminal or command prompt and execute the following command to pull the MongoDB Docker image
   ```shell
   docker pull mongo:5
   ```
   This command fetches the latest official MongoDB image from the Docker Hub repository.
3. Create a Docker Container: Once the image is downloaded, you can create a MongoDB container by executing the following command:
   ```shell
   docker run --name my-mongodb -d -p 27017:27017 mongo:5
   ```
   Let's break down the command:
   **--name my-mongodb** specifies a name for the container. You can choose any name you prefer.
   **-d** runs the container in detached mode.
   **-p 27017:27017** maps the container's port 27017 to the host's port 27017, allowing you to access MongoDB on your local machine.
   **mongo:5** specifies the name of the Docker image to use for the container.
   Running this command creates a new MongoDB container named "my-mongodb" and starts it in the background.
4. Verify Container Status: You can check the status of your MongoDB container by executing the `docker ps` command:
      ```
   ➜  docker ps
   CONTAINER ID   IMAGE     COMMAND                  CREATED         STATUS         PORTS                      NAMES
   22f402430963   mongo:5   "docker-entrypoint.s…"   8 minutes ago   Up 8 minutes   0.0.0.0:27017->27017/tcp   my-mongodb
   ```
   This command lists all running containers. Ensure that your MongoDB container is listed and has a status of "Up".
5. Connect to MongoDB:
   To connect to MongoDB running in the Docker container, you can use any MongoDB client or the MongoDB shell.
   Connect to it using the following command:
   ```shell
   mongosh --host localhost --port 27017
   ```
   This command connects to the MongoDB server running in the Docker container on your local machine.
   - If MongoDB is installed and running correctly, you should see the MongoDB wellcome message:
   ```shell
   Current Mongosh Log ID:	64b54d1b599bb2be32f80860
   Connecting to:		mongodb://localhost:27017/?directConnection=true&serverSelectionTimeoutMS=2000&appName=mongosh+1.10.1
   Using MongoDB:		5.0.19
   Using Mongosh:		1.10.1

   For mongosh info see: https://docs.mongodb.com/mongodb-shell/

   ------
      The server generated these startup warnings when booting
      2023-07-17T14:15:12.101+00:00: Using the XFS filesystem is strongly recommended with the WiredTiger storage engine. See http://dochub.mongodb.org/core/prodnotes-filesystem
      2023-07-17T14:15:12.731+00:00: Access control is not enabled for the database. Read and write access to data and configuration is unrestricted
   ------
   ```


### MongoDB Setup (Docker) - stop and remove:
1. List Running Containers: Open a terminal or command prompt and execute `docker ps` command to list all running containers:
   ```
   ➜  docker ps
   CONTAINER ID   IMAGE     COMMAND                  CREATED         STATUS         PORTS                      NAMES
   22f402430963   mongo:5   "docker-entrypoint.s…"   8 minutes ago   Up 8 minutes   0.0.0.0:27017->27017/tcp   my-mongodb
   ```
   This command displays a list of running containers along with their details.
2. Identify the MongoDB Container: From the list of running containers,
   locate the MongoDB container you want to stop and remove. Note down the container ID or the container name.
3. Stop the Container: To stop the MongoDB container, execute the following command,
   replacing `<container_id>` or `<container_name>` with the appropriate value:
   ```shell
   docker stop <container_id or container_name>
   ```
   For example, if the container ID is 22f402430963, the command would be:
   ```shell
   docker stop 22f402430963
   ```
   This command gracefully stops the MongoDB container.
4. Remove the Container: Once the container is stopped, you can remove it by executing the following command,
   again replacing `<container_id>` or `<container_name>` with the appropriate value:
   ```shell
   docker rm <container_id or container_name>
   ```
   For example, if the container ID is 22f402430963, the command would be:
   ```shell
   docker rm 22f402430963
   ```
   This command removes the MongoDB container from your system.
5. Verify Container Removal: You can verify that the container has been successfully removed by executing the docker ps command again.
   The MongoDB container should no longer be listed among the running containers.

### MongoDB GUI (Robo 3T)
Once you have MongoDB installed, you can install a MongoDB GUI tool to easily view and manage the data generated by your sandbox application. [Robo 3T](https://robomongo.org/) is a popular GUI tool that is simple to use. Follow the steps below to install Robo 3T:

1. Download Robo 3T:
    - Visit the official Robo 3T website: [https://robomongo.org/](https://robomongo.org/)
    - Download the appropriate version of Robo 3T.

2. Install Robo 3T:
   - Run the downloaded installer and follow the installation wizard.
   - Select the desired installation location and any additional preferences.
   - Once the installation is complete, proceed to the next step.

3. Connect Robo 3T to MongoDB:
    - Launch Robo 3T from your applications or the directory where it was installed.
    - In the Robo 3T interface, click on the "New Connection" button to create a new connection.
    - Provide a name for the connection (e.g., "Local MongoDB") and specify the connection details:
        - **Protocol**: Set it to `mongodb`
        - **Address**: Set it to `localhost` or `127.0.0.1`.
        - **Port**: Set it to `27017` (the default MongoDB port).
        - example: `mongodb://127.0.0.1:27017`
    - Click "Next" to go to the next screen.
    - Provide connection-name and save it.
    - Click on the newly created connection to connect to your MongoDB instance.

4. Start exploring and managing your MongoDB data:
    - With Robo 3T connected to your MongoDB instance, you can now explore the databases, collections, and documents.
    - Use the intuitive interface provided by Robo 3T to view, insert, update, and delete data as needed.
    - Take advantage of the various features and capabilities offered by Robo 3T to effectively work with your MongoDB data.

### SBT

Finally, to be able to compile and run the solution you will need `sbt` installed (info [here](https://www.scala-sbt.org/index.html)). To install it, run the following command:

- With SDKMan: `sdk install sbt`
- With Brew (another widely used package manager, info [here](https://brew.sh/)): `brew install sbt`

### Finally, run it!

To run the application, execute `sbt run` and it will start it in port `9335`. [http://localhost:9335/hmrc-frontend-sandbox/](http://localhost:9335/hmrc-frontend-sandbox/) will be the root of your site (index)

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

## User Answers

[`UserAnswers`](./app/models/UserAnswers.scala) is the model that we use to represent and store the data gathered by the site as the user progresses through the journey. At its core, the model can be seen as a JSON container that will hold all data gathered with two more properties to make it unique to the user:

- `id` property that is used to store the unique identifier of the user (plase note that as mentioned above, the id for the user has been stubbed and it will always be `test`, have another look [here](https://github.com/opencastsoftware/hmrc-frontend-sandbox/blob/master/app/controllers/actions/IdentifierAction.scala#L45))
- `data` which can be seen as a container for the `JSON` object that will contain all journey data.
- `lastUpdated`

Within mongoDB, user answers will look something like this:

```
{
    "_id" : "test",
    "data" : {
        "firstNumber" : 10,
        "secondNumber" : 20
    },
    "lastUpdated" : ISODate("2022-01-13T10:04:29.777Z")
}

```

Please note that `firstNumber` and `secondNumber` represent the journey [pages](./app/pages) that the user goes through. For example, you can see the `JSON` path for the `First Number` page together with its name [here](./app/pages/FirstNumberPage.scala).


## Site parameters (configuration)

You can see all site configuration parameters within the [`application.conf`](./conf/application.conf).

## Common issues:

## Common Issue: Unexpected exception [UncheckedExecutionException: java.lang.IllegalStateException: Unable to load cache item]
```text
! @829be645f - Internal server error, for (GET) [/] ->] exception=[play.api.UnexpectedException: Unexpected exception[UncheckedExecutionException: java.lang.IllegalStateException: Unable to load cache item]
	at play.core.server.DevServerStart$$anon$1.reload(DevServerStart.scala:254)
	at play.core.server.DevServerStart$$anon$1.get(DevServerStart.scala:148)
	at play.core.server.AkkaHttpServer.handleRequest(AkkaHttpServer.scala:310)
	at play.core.server.AkkaHttpServer.$anonfun$createServerBinding$1(AkkaHttpServer.scala:227)
	at akka.stream.impl.fusing.MapAsync$$anon$30.onPush(Ops.scala:1309)
	at akka.stream.impl.fusing.GraphInterpreter.processPush(GraphInterpreter.scala:542)
	at akka.stream.impl.fusing.GraphInterpreter.processEvent(GraphInterpreter.scala:496)
	at akka.stream.impl.fusing.GraphInterpreter.execute(GraphInterpreter.scala:390)
	at akka.stream.impl.fusing.GraphInterpreterShell.runBatch(ActorGraphInterpreter.scala:650)
	at akka.stream.impl.fusing.GraphInterpreterShell$AsyncInput.execute(ActorGraphInterpreter.scala:521)
	at akka.stream.impl.fusing.GraphInterpreterShell.processEvent(ActorGraphInterpreter.scala:625)
	at akka.stream.impl.fusing.ActorGraphInterpreter.akka$stream$impl$fusing$ActorGraphInterpreter$$processEvent(ActorGraphInterpreter.scala:800)
	at akka.stream.impl.fusing.ActorGraphInterpreter$$anonfun$receive$1.applyOrElse(ActorGraphInterpreter.scala:818)
	at akka.actor.Actor.aroundReceive(Actor.scala:537)
	at akka.actor.Actor.aroundReceive$(Actor.scala:535)
	at akka.stream.impl.fusing.ActorGraphInterpreter.aroundReceive(ActorGraphInterpreter.scala:716)
	at akka.actor.ActorCell.receiveMessage(ActorCell.scala:579)
	at akka.actor.ActorCell.invoke(ActorCell.scala:547)
	at akka.dispatch.Mailbox.processMailbox(Mailbox.scala:270)
	at akka.dispatch.Mailbox.run(Mailbox.scala:231)
	at akka.dispatch.Mailbox.exec(Mailbox.scala:243)
	at java.base/java.util.concurrent.ForkJoinTask.doExec(ForkJoinTask.java:373)
	at java.base/java.util.concurrent.ForkJoinPool$WorkQueue.topLevelExec(ForkJoinPool.java:1182)
	at java.base/java.util.concurrent.ForkJoinPool.scan(ForkJoinPool.java:1655)
	at java.base/java.util.concurrent.ForkJoinPool.runWorker(ForkJoinPool.java:1622)
	at java.base/java.util.concurrent.ForkJoinWorkerThread.run(ForkJoinWorkerThread.java:165)
```
If you encounter the error message "Unexpected exception [UncheckedExecutionException: java.lang.IllegalStateException: Unable to load cache item]" during the execution of your application,
it may indicate a problem with the default Java version being used.

### Solution:
### Solution:
1. Install SDKMAN:
   - SDKMAN is a software development kit manager that simplifies managing multiple Java versions. If you haven't already installed SDKMAN, follow the steps below:
      - Open a terminal or command prompt.
      - Run the following command to install SDKMAN:
        ```shell
        curl -s "https://get.sdkman.io" | bash
        ```
      - Follow the on-screen instructions to complete the installation.
      - Close and reopen the terminal or command prompt to reload the shell configuration.

2. Install Java version 1.8 using SDKMAN:
   - Open a new terminal or command prompt.
   - Run the following command to install Java version 8.0.352-amzn using SDKMAN:
     ```shell
     sdk install java 8.0.352-amzn
     ```
   - SDKMAN will download and install the specified Java version. This process may take a few moments.

3. Set Java version 1.8 as the default:
   - Run the following command to set Java 8.0.352-amzn as the default Java version:
     ```shell
     sdk default java 8.0.352-amzn
     ```
   - SDKMAN will update the default Java version to 8.0.352-amzn.

4. Test your application:
   - Re-run your application or command that was previously causing the error.
   - The "Unexpected exception" error should no longer occur if the Java version has been successfully updated.

