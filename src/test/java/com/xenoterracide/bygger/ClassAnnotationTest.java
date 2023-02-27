package com.xenoterracide.bygger;

import com.google.testing.compile.JavaFileObjects;
import org.junit.jupiter.api.Test;

import static com.google.testing.compile.Compiler.javac;
import static org.assertj.core.api.Assertions.assertThat;

public class ClassAnnotationTest {
  @Test
  void testImmutable() {
    var comp = javac().compile(
      JavaFileObjects.forSourceLines(
        "cxb.Example",
        "package cxb;"
      )
    );
    assertThat( comp.errors() ).isEmpty();
  }
}
