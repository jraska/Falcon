package com.jraska.falcon;

import android.app.Activity;
import com.squareup.spoon.Spoon;

import java.io.File;

/**
 * Utility class which simply uses Spoon API to obtain desired file
 * and then write there Spoon screenshot
 */
public final class FalconSpoon {
  //region Methods

  public static File takeScreenshot(Activity activity, String tag) {
    File screenshot = Spoon.screenshot(activity, tag);

    // File will be overwritten with new screenshot
    Falcon.takeScreenshot(activity, screenshot);

    return screenshot;
  }

  public static File takeScreenshot(Activity activity, String tag, String testClassName,
                                    String testMethodName) {
    File screenshot = Spoon.screenshot(activity, tag, testClassName, testMethodName);

    // File will be overwritten with new screenshot
    Falcon.takeScreenshot(activity, screenshot);

    return screenshot;
  }

  //endregion

  //region Constructors

  private FalconSpoon() {
    // No instances
  }

  //endregion
}
