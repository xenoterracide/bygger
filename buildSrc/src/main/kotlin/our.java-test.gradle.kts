import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import java.io.FileNotFoundException

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
  val ss = sourceSets;
  doLast {
    ss.getByName("test") {
      if (java.isEmpty) throw FileNotFoundException("no tests found")
    }
  }
}

tasks.test.configure {
  useJUnitPlatform()

  // See: https://github.com/google/compile-testing/issues/222
  jvmArgs = listOf(
    "--add-exports=jdk.compiler/com.sun.tools.javac.api=ALL-UNNAMED",
    "--add-exports=jdk.compiler/com.sun.tools.javac.main=ALL-UNNAMED",
    "--add-exports=jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED"
  )

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
