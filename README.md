# InsightEdge Examples
##### _InsightEdge GigaSpaces convergence platform_
-----------------------------------------

## Introduction

[InsightEdge](http://insightedge.io/) is a Hybrid transactional/analytical processing platform built on top of Spark and GigaSpaces Data Grid.

This project contains examples of how standard Spark applications can use Data Grid for different purposes.

## Requirements
* Java 1.8
* Scala 2.10
* Maven 3.1+
* SBT 0.13.9+ (optional)
* InsightEdge distribution


## Building project

InsightEdge jars are not published to Maven Central Repository yet. To install artifacts to your local Maven repository, make sure you have Maven installed and then run:
```bash
# Linux:
./sbin/insightedge-maven.sh

# Windows:
sbin\insightedge-maven.cmd
```

This project has both SBT and Maven configurations. You can build it with next commands:

```bash
# Maven
mvn clean test package

# SBT
sbt clean test assembly
```


## Running examples

There are several options how you can run examples:
* from Web Notebook (refer to corresponding section in [InsightEdge documentation](http://insightedge.io/docs))
* from your IDE
* from a command line

#### Starting local environment

Prior to executing example application, you have to start the Data Grid and deploy an empty space on it. You can do it using `demo` mode:
```bash
# Linux:
./sbin/insightedge.sh --mode demo

# Windows:
sbin\insightedge.cmd --mode demo
```

Such command will start next components:
* Spark master at `spark://127.0.0.1:7077` and Spark slave
* Data Grid manager and two containers with `1G` heap each
    - space is deployed with name `insightedge-space`
    - lookup locator is `127.0.0.1:4174`
    - lookup group is `insightedge`

#### Running from IDE

You can run examples from your favourite IDE. Every example has a `main` method, so it can be executed as standard application.

Here is an example of run configuration for `SaveRDD` for `Intellij Idea`:
![IDEA run configuration](doc/images/idea-configuration.png?raw=true)

With this configuration, example will run on local Spark cluster and save the generated RDD to Data Grid to specified space.

#### Running from command line

You can build the project and submit examples as Spark applications with the next command:
```bash
# Linux:
./bin/insightedge-submit --class {main class name} --master {Spark master URL} \
    {insightedge-examples.jar location} \
    {Spark master URL} {space name} {lookup group} {lookup locator}

# Windows:
bin\insightedge-submit --class {main class name} --master {Spark master URL} ^
    {insightedge-examples.jar location} ^
    {Spark master URL} {space name} {lookup group} {lookup locator}
```

For example, `SaveRDD` can be submitted with the next syntax:
```bash
# Linux:
./bin/insightedge-submit --class com.gigaspaces.insightedge.examples.basic.SaveRdd --master spark://127.0.0.1:7077 \
    ./quickstart/scala/insightedge-examples.jar \
    spark://127.0.0.1:7077 insightedge-space insightedge 127.0.0.1:4174

# Windows:
bin\insightedge-submit --class com.gigaspaces.insightedge.examples.basic.SaveRdd --master spark://127.0.0.1:7077 ^
    quickstart\scala\insightedge-examples.jar ^
    spark://127.0.0.1:7077 insightedge-space insightedge 127.0.0.1:4174
```

If you are running local cluster with default settings (see [Running Examples](#running-examples)), you can omit arguments:
```bash
# Linux:
./bin/insightedge-submit --class {main class name} --master {Spark master URL} \
    {insightedge-examples.jar location}

# Windows:
bin\insightedge-submit --class {main class name} --master {Spark master URL} ^
    {insightedge-examples.jar location}
```

> Note that running `TwitterPopularTags` example requires you to pass [Twitter app tokens](https://apps.twitter.com/) as arguments

#### Python examples

You can run Python examples with
```bash
# Linux:
./bin/insightedge-submit --master {Spark master URL} {path to .py file}

# Windows:
bin\insightedge-submit --master {Spark master URL} {path to .py file}
```

For example,
```bash
# Linux:
./bin/insightedge-submit --master spark://127.0.0.1:7077 ./quickstart/python/sf_salaries.py

# Windows:
bin\insightedge-submit --master spark://127.0.0.1:7077 quickstart\python\sf_salaries.py
```

#### Stopping local environment

To stop all InsightEdge components, next command can be executed:
```bash
# Linux:
./sbin/insightedge.sh --mode shutdown

# Windows:
sbin\insightedge.cmd --mode shutdown
```


## Troubleshooting

If you have any troubles running the example applications, please, contact us with:
- Slack channel using [invitation](http://insightedge-slack.herokuapp.com/)
- StackOverflow [insightedge tag](http://stackoverflow.com/questions/tagged/insightedge)
- contact form at [InsightEdge main page](http://insightedge.io/)
- or [email message](mailto:hello@insightedge.io)