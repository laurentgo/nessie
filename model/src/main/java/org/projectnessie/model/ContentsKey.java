/*
 * Copyright (C) 2020 Dremio
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.projectnessie.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.immutables.value.Value;

/**
 * Key for the contents of an object.
 *
 * <p>For URL encoding, embedded periods within a segment are replaced with zero byte values before
 * passing in a url string.
 */
@Value.Immutable(prehash = true)
@JsonSerialize(as = ImmutableContentsKey.class)
@JsonDeserialize(as = ImmutableContentsKey.class)
public abstract class ContentsKey {

  private static final char ZERO_BYTE = '\u0000';
  private static final String ZERO_BYTE_STRING = Character.toString(ZERO_BYTE);

  public abstract List<String> getElements();

  /**
   * Returns the namespace that is always consisting of the first <b>N-1</b> elements from {@link
   * ContentsKey#getElements()}.
   *
   * @return A {@link Namespace} instance that is always consisting of the first <b>N-1</b> elements
   *     from {@link ContentsKey#getElements()}.
   */
  @JsonIgnore
  @Value.Derived
  public Namespace getNamespace() {
    return Namespace.of(getElements());
  }

  public static ContentsKey of(String... elements) {
    return ImmutableContentsKey.builder().elements(Arrays.asList(elements)).build();
  }

  @JsonCreator
  public static ContentsKey of(@JsonProperty("elements") List<String> elements) {
    return ImmutableContentsKey.builder().elements(elements).build();
  }

  @Value.Check
  protected void validate() {
    for (String e : getElements()) {
      if (e.contains(ZERO_BYTE_STRING)) {
        throw new IllegalArgumentException("An object key cannot contain a zero byte.");
      }
    }
  }

  /**
   * Convert from path encoded string to normal string.
   *
   * @param encoded Path encoded string
   * @return Actual key.
   */
  public static ContentsKey fromPathString(String encoded) {
    List<String> elements =
        Arrays.stream(encoded.split("\\."))
            .map(x -> x.replace('\u0000', '.'))
            .collect(Collectors.toList());
    return of(elements);
  }

  /**
   * Convert this key to a url encoded path string.
   *
   * @return String encoded for path use.
   */
  public String toPathString() {
    return getElements().stream()
        .map(x -> x.replace('.', '\u0000'))
        .collect(Collectors.joining("."));
  }

  @Override
  public String toString() {
    return String.join(".", getElements());
  }
}
