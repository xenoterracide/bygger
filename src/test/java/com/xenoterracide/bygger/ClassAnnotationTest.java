package com.xenoterracide.bygger;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;

import com.google.testing.compile.JavaFileObjects;
import com.xenoterracide.bygger.processor.ByggerProcessor;
import org.junit.jupiter.api.Test;

class ClassAnnotationTest {

  @Test
  void testImmutable() {
    var source =
      """
      import com.xenoterracide.bygger.annotations.ValueObject;
      import javax.annotation.Nonnull;

      @ValueObject
      public interface HelloWorld {
        @Nonnull String getHello();
        @Nonnull String getWorld();
      }
      """;

    var compilation = javac()
      .withProcessors(new ByggerProcessor())
      .compile(JavaFileObjects.forSourceString("HelloWorld", source));

    assertThat(compilation).succeeded();
  }
}
