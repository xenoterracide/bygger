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
package com.xenoterracide.bygger.processor;

import com.google.auto.service.AutoService;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

@SupportedAnnotationTypes("com.xenoterracide.bygger.annotations.*")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
@AutoService(Processor.class)
public class ByggerProcessor extends AbstractProcessor {

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    var map = annotations
      .stream()
      .map(roundEnv::getElementsAnnotatedWith)
      .flatMap(Set::stream)
      .collect(Collectors.toUnmodifiableMap(Element::getKind, Stream::of));

    map
      .getOrDefault(ElementKind.INTERFACE, Stream.empty())
      .map(Element::getClass)
      .forEach(
        Try.of(clazz -> {
          this.processingEnv.getFiler().createSourceFile(clazz.getCanonicalName() + "Builder");
        })
      );
    return false;
  }
}
