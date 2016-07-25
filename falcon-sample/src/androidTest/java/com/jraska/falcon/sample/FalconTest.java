package com.jraska.falcon.sample;

import android.graphics.Bitmap;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.jraska.falcon.Falcon;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import static com.jraska.falcon.sample.matchers.BitmapFileMatcher.isBitmap;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

/**
 * Shows usage of {@link Falcon} screenshots
 */
@RunWith(AndroidJUnit4.class)
public class FalconTest {
  //region Constants

  static final int SMALLEST_SCREEN_EVER = 100;

  //endregion

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
    SampleActivity activity = _activityRule.getActivity();
    File newFile = activity.getScreenshotFile();
    _screenshotFile = newFile;

    //check that file does not exist yet
    assertThat(newFile.exists(), is(false));

    Falcon.takeScreenshot(activity, newFile);

    assertThat(newFile, isBitmap());
  }

  @Test
  public void takesScreenshotToBitmap() throws Exception {
    Bitmap bitmap = Falcon.takeScreenshotBitmap(_activityRule.getActivity());

    assertThat(bitmap, not(nullValue()));

    assertThat(bitmap.getWidth(), greaterThan(SMALLEST_SCREEN_EVER));
    assertThat(bitmap.getHeight(), greaterThan(SMALLEST_SCREEN_EVER));
  }

  public void takesDialogIntoScreenshot() throws Exception {

  }

  //endregion
}
