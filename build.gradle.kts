group = "com.xenoterracide"
version = "0.1.0-SNAPSHOT"

plugins {
  `java-library`
  id("our.java-library")
}

dependencies {
  implementation(platform(libs.spring.platform))
  implementation(libs.java.parser)
  implementation(libs.autoservice)
  implementation(libs.commons.lang)
  testImplementation(libs.compile.testing)
}

tasks.dependencies {
  dependsOn(subprojects.map { it.tasks.dependencies })
}
