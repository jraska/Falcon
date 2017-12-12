package com.jraska.falcon;

import android.app.Activity;
import com.squareup.spoon.SpoonRule;

import java.io.File;

/**
 * Utility class which simply uses Spoon API to obtain desired file
 * and then write there Falcon screenshot
 */
public final class FalconSpoonRule {
  //region Public API

  public static File screenshot(SpoonRule spoon, Activity activity, String tag) {
    File screenshot = spoon.screenshot(activity, tag);

    // File will be overwritten with new screenshot
    Falcon.takeScreenshot(activity, screenshot);

    return screenshot;
  }

  //endregion

  //region Constructors

  private FalconSpoonRule() {
    // No instances
  }

  //endregion
}
