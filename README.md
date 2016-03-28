# InsightEdge Examples
##### _InsightEdge GigaSpaces convergence platform_
-----------------------------------------

## Introduction

[InsightEdge](http://insightedge.io/) is a Hybrid transactional/analytical processing platform built on top of Spark and GigaSpaces Data Grid.

This project contains examples of how standard Spark applications can use Data Grid for different purposes.

## Requirements
* Java 1.8
* Scala 2.10
* SBT 0.13
* InsightEdge distribution


## Building project

This project is based on SBT, so to build it run the next command:

```bash
sbt clean package
```


## Running examples

There are several options how you can run examples:
* from Web Notebook (refer to corresponding section in [InsightEdge documentation](http://insightedge.io/docs))
* from your IDE
* from a command line

#### Starting local environment

Prior to executing example application, you have to start the Data Grid and deploy an empty space on it. You can do it using `demo` mode:
```bash
./sbin/insightedge.sh --mode demo
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
./bin/insightedge-submit --class {main class name} --master {Spark master URL} \
    {insightedge-examples.jar location} \
    {Spark master URL} {space name} {lookup group} {lookup locator}
```

For example, `SaveRDD` can be submitted with the next syntax:
```bash
./bin/insightedge-submit --class com.gigaspaces.insightedge.examples.basic.SaveRdd --master spark://127.0.0.1:7077 \
    {repository}/target/scala-2.10/insightedge-examples*.jar \
    spark://127.0.0.1:7077 insightedge-space insightedge 127.0.0.1:4174
```

#### Stopping local environment

To stop all InsightEdge components, next command can be executed:
```bash
./sbin/insightedge.sh --mode shutdown
```


## Troubleshooting

If you have any troubles running the example applications, please, contact us via contact form at [InsightEdge main page](http://insightedge.io/).