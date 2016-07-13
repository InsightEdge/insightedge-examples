try {
    sh "sbt clean test assembly -no-colors"
} finally {
    sh "sbt clean test assembly -no-colors"
}


sh "mvn clean install"

