// Copyright 2000-2020 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.intellij.util.lang;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Predicate;

/**
 * An object responsible for loading classes and resources from a particular classpath element: a jar or a directory.
 *
 * @see JarLoader
 * @see FileLoader
 */
abstract class Loader {
  final @NotNull Path path;
  private Predicate<String> nameFilter;

  Loader(@NotNull Path path) {
    this.path = path;
  }

  abstract @Nullable Resource getResource(@NotNull String name);

  abstract @NotNull ClasspathCache.IndexRegistrar buildData() throws IOException;

  final boolean containsName(@NotNull String name, @NotNull String shortName) {
    if (name.isEmpty()) {
      return true;
    }

    Predicate<String> filter = nameFilter;
    return filter == null || filter.test(shortName);
  }

  final void setNameFilter(@NotNull Predicate<String> filter) {
    nameFilter = filter;
  }
}
