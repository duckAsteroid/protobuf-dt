/*
 * Copyright (c) 2011 Google Inc.
 *
 * All rights reserved. This program and the accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 *
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.google.eclipse.protobuf.scoping;

import static org.eclipse.emf.common.util.URI.createURI;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

import org.eclipse.emf.common.util.URI;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for <code>{@link ImportUriFixer#fixUri(String, URI, ResourceChecker)}</code>.
 *
 * @author alruiz@google.com (Alex Ruiz)
 */
public class ImportUriFixer_fixUri_Test {

  private URI resourceUri;
  private ResourceChecker resourceChecker;
  private ImportUriFixer fixer;

  @Before public void setUp() {
    resourceUri = createURI("platform:/resource/testing/src/test.proto");
    resourceChecker = mock(ResourceChecker.class);
    fixer = new ImportUriFixer();
  }

  @Test public void should_fix_import_URI_if_missing_scheme() {
    String expected = "platform:/resource/testing/src/folder1/test.proto";
    when(resourceChecker.resourceExists(expected)).thenReturn(true);
    String fixed = fixer.fixUri("folder1/test.proto", resourceUri, resourceChecker);
    assertThat(fixed, equalTo(expected));  
  }

  @Test public void should_not_fix_import_URI_if_not_missing_scheme() {
    String importUri = "platform:/resource/testing/src/folder1/test.proto";
    String fixed = fixer.fixUri(importUri, resourceUri, resourceChecker);
    assertThat(fixed, equalTo(importUri));
  }
  
  @Test public void should_fix_import_URI_even_if_overlapping_folders_with_resource_URI() {
    String expected = "platform:/resource/testing/src/folder1/test.proto";
    when(resourceChecker.resourceExists(expected)).thenReturn(true);
    String fixed = fixer.fixUri("testing/src/folder1/test.proto", resourceUri, resourceChecker);
    assertThat(fixed, equalTo(expected));  
  }
}
