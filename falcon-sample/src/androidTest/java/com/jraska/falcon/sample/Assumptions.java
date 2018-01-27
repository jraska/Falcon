package com.jraska.falcon.sample;

import android.os.Build;
import org.junit.Assume;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

public final class Assumptions {
  public static void assumeNoCI() {
    Assume.assumeThat("Test should not run on CI server.", isContinuousIntegration(), is(false));
  }

  public static boolean isContinuousIntegration() {
    return BuildConfig.CI_BUILD;
  }
}
