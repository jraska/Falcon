package com.jraska.falcon.sample;

import org.junit.Assume;

import static org.hamcrest.Matchers.is;

public final class CICheck {
  public static void assumeNoCI(){
    Assume.assumeThat("Test should not run on CI server.", isContinuousIntegration(), is(false));
  }

  public static boolean isContinuousIntegration() {
    return Boolean.parseBoolean(System.getenv("CI"));
  }
}
