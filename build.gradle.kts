group = "com.xenoterracide"
version = "0.1.0-SNAPSHOT"

plugins {
  `java-library`
  id("our.java-library")
}

tasks.dependencies {
  dependsOn(subprojects.map { it.tasks.dependencies })
}
