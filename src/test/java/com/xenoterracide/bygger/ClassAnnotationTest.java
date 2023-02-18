package com.xenoterracide.bygger;

import static com.google.testing.compile.Compiler.javac;

import com.google.testing.compile.JavaFileObjects;
import org.junit.jupiter.api.Test;

public class ClassAnnotationTest {
  @Test
  void testImmutable() {
    javac().compile(
      JavaFileObjects.forSourceLines(
        "cxb.Example",
        "package cxb;"
      )
    );
  }
}
