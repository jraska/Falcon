package com.jraska.falcon;

import android.app.Activity;
import android.content.Context;

import com.squareup.spoon.SpoonRule;

import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.io.File;

/**
 * Utility class which simply uses Spoon API to obtain desired file
 * and then write there Falcon screenshot
 */
public final class FalconSpoonRule implements TestRule {

  //region Fields

  @Rule
  private final SpoonRule spoonRule = new SpoonRule();

  //endregion

  //region Public API
  @Override
  public Statement apply(Statement base, Description description) {
    return spoonRule.apply(base, description);
  }

  public File save(Context context, File file) {
    return spoonRule.save(context, file);
  }

  public File screenshot(Activity activity, String tag) {
    File screenshot = spoonRule.screenshot(activity, tag);

    // File will be overwritten with new screenshot
    Falcon.takeScreenshot(activity, screenshot);

    return screenshot;
  }

  //endregion

  //region Constructors

  public FalconSpoonRule() {
    // empty constructor, just like SpoonRule
  }

  //endregion
}
