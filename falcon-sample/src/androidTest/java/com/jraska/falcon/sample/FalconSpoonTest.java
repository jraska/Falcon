package com.jraska.falcon.sample;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.jraska.falcon.FalconSpoon;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import static com.jraska.falcon.sample.matchers.BitmapFileMatcher.isBitmap;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Shows usage of {@link FalconSpoon} compat screenshots
 */
@RunWith(AndroidJUnit4.class)
public class FalconSpoonTest {

  //region Fields

  @Rule
  public ActivityTestRule<SampleActivity> _activityRule = new ActivityTestRule<>(
      SampleActivity.class);

  private File _screenshotFile;

  //endregion

  //region Setup Methods

  @After
  public void after() throws Exception {
    if (_screenshotFile != null) {
      assertThat(_screenshotFile.delete(), is(true));
    }
  }

  //endregion

  //region Test methods

  @Test
  public void takesScreenshotToFile() throws Exception {
    String tag = "ExampleScreenshot";
    _screenshotFile = FalconSpoon.screenshot(_activityRule.getActivity(), tag);

    assertThat(_screenshotFile.length(), greaterThan(0L));
    assertThat(_screenshotFile, isBitmap());
  }

  @Test
  public void takesScreenshotToBitmap() throws Exception {
    String tag = "ExampleScreenshot";

    _screenshotFile = FalconSpoon.screenshot(_activityRule.getActivity(), tag,
        "FalconSpoonTest", "CustomMethodName");

    assertThat(_screenshotFile.length(), greaterThan(0L));
    assertThat(_screenshotFile, isBitmap());
  }

  //endregion
}
