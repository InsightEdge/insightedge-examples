stage 'Build'
env.PATH = "${tool 'maven-3.3.9'}/bin:$env.PATH"
env.PATH = "$env.SBT_HOME/bin:$env.PATH"
load 'tools/build.groovy'