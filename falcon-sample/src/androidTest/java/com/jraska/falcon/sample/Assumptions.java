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

  public static void assumeSpoonPermissions() {
    // TODO: 28/07/16 PULL WELCOME: grant permissions to test application on API 21+
    Assume.assumeThat("Spoon cannot take screenshots on API 21+" +
            " without granted WRITE_EXTERNAL_STORAGE permission",
        Build.VERSION.SDK_INT, lessThan(Build.VERSION_CODES.LOLLIPOP));
  }
}
