stage 'Build'
env.PATH = "${tool 'maven-3.3.9'}/bin:$env.PATH"
load 'tools/build.groovy'