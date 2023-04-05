/*
 * Copyright 2021 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openrewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.test.RecipeSpec;
import org.openrewrite.test.RewriteTest;
import org.openrewrite.test.SourceSpecs;

import static org.assertj.core.api.Assertions.assertThat;
import static org.openrewrite.java.Assertions.java;
import static org.openrewrite.test.SourceSpecs.text;

public class LanguageCompositionTest implements RewriteTest {

    @Override
    public void defaults(RecipeSpec spec) {
        spec.recipe(new LanguageComposition());
    }

    @Test
    void countsJava() {
        rewriteRun(
            spec -> spec.dataTable(LanguageCompositionReport.Row.class, table -> {
                assertThat(table).hasSize(1);
                LanguageCompositionReport.Row row = table.get(0);
                assertThat(row.getJavaLineCount()).isEqualTo(3);
                assertThat(row.getJavaFileCount()).isEqualTo(1);
                assertThat(row.getPlainTextFileCount()).isEqualTo(1);
                assertThat(row.getPlainTextLineCount()).isGreaterThan(0);
            }),
            //language=java
            java("""
                package com.whatever;
                
                class A {
                    void foo() {
                    }
                }
                """),
            text("""
                hello
                world
                
                """)

        );
    }
}
