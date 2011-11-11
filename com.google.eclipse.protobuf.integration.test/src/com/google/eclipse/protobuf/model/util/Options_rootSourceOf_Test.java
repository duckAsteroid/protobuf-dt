/*
 * Copyright (c) 2011 Google Inc.
 *
 * All rights reserved. This program and the accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 *
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.google.eclipse.protobuf.model.util;

import static com.google.eclipse.protobuf.junit.core.Setups.integrationTestSetup;
import static com.google.eclipse.protobuf.junit.core.XtextRule.createWith;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.*;

import com.google.eclipse.protobuf.junit.core.XtextRule;
import com.google.eclipse.protobuf.protobuf.*;

/**
 * Tests for <code>{@link Options#rootSourceOf(Option)}</code>.
 *
 * @author alruiz@google.com (Alex Ruiz)
 */
public class Options_rootSourceOf_Test {

  @Rule public XtextRule xtext = createWith(integrationTestSetup());

  private Options options;

  @Before public void setUp() {
    options = xtext.getInstanceOf(Options.class);
  }

  // syntax = "proto2";
  //
  // option java_package = 'com.google.eclipse.protobuf.tests';
  @Test public void should_return_property_of_native_option() {
    Option option = xtext.find("java_package", Option.class);
    Property p = (Property) options.rootSourceOf(option);
    assertThat(p.getName(), equalTo("java_package"));
  }

  // syntax = "proto2";
  //
  // import 'google/protobuf/descriptor.proto';
  //
  // extend google.protobuf.FileOptions {
  //   optional string encoding = 1000;
  // }
  //
  // option (encoding) = 'UTF-8';
  @Test public void should_return_property_of_custom_option() {
    Option option = xtext.find("encoding", ")", Option.class);
    Property p = (Property) options.rootSourceOf(option);
    assertThat(p.getName(), equalTo("encoding"));
  }
}