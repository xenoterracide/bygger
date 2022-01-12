plugins {
  `java-library`
  id("our.bom")
  id("our.java-compile")
  id("our.java-test")
  id("our.checkstyle")
  id("our.spotbugs")
  id("our.spotless")
  id("our.coverage")
}

dependencies {
  runtimeOnly("org.springframework.boot:spring-boot-starter-log4j2")
}
