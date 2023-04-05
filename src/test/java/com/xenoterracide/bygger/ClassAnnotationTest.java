/**
Copyright © 2023 Caleb Cushing.
Apache 2.0. See https://github.com/xenoterracide/brix/LICENSE.txt
https://choosealicense.com/licenses/apache-2.0/#

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OFS ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

 **/
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
