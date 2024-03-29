import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
  `java-library`
  `java-test-fixtures`
}

repositories {
  mavenCentral()
}
val libs = the<LibrariesForLibs>()

dependencies {
  implementation(libs.log4j.api)
  testImplementation(platform(libs.junit.bom))
  testImplementation(libs.bundles.test)
  testRuntimeOnly(libs.bundles.junit.platform)
}

val available = tasks.register("tests available") {
  val java: Provider<FileCollection> = sourceSets.test.map { it.java }
  doLast {
    if (java.get().isEmpty) throw RuntimeException("no tests found")
  }
}

tasks.withType<Test>().configureEach {
  useJUnitPlatform()

  // See: https://github.com/google/compile-testing/issues/222
  jvmArgs("--add-exports=jdk.compiler/com.sun.tools.javac.api=ALL-UNNAMED")
  jvmArgs("--add-exports=jdk.compiler/com.sun.tools.javac.main=ALL-UNNAMED")
  jvmArgs("--add-exports=jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED")

  testLogging {
    lifecycle {
      showStandardStreams = true
      displayGranularity = 2
      exceptionFormat = TestExceptionFormat.FULL
      events.addAll(listOf(TestLogEvent.STARTED, TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.FAILED))
    }
  }
  reports {
    html.required.set(false)
    junitXml.required.set(false)
  }
  inputs.dir(rootProject.file("buildSrc/src/main"))
  finalizedBy(available)

  afterSuite(
    KotlinClosure2<TestDescriptor, TestResult, Unit>(
      { descriptor, result ->
        if (descriptor.parent == null) {
          logger.lifecycle("Tests run: ${result.testCount}, Failures: ${result.failedTestCount}, Skipped: ${result.skippedTestCount}")
          if (result.testCount == 0L) throw IllegalStateException("You cannot have 0 tests");
        }
        Unit
      })
  )
}
