package com.jraska.falcon.sample;

import android.graphics.Bitmap;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import com.jraska.falcon.Falcon;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import static com.jraska.falcon.sample.asserts.BitmapFileAssert.assertThatFile;
import static org.assertj.core.api.Assertions.assertThat;


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
      assertThat(_screenshotFile.delete()).isTrue();
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
    assertThat(newFile).doesNotExist();

    Falcon.takeScreenshot(activity, newFile);

    assertThatFile(newFile).isBitmap();
  }

  @Test
  public void takesScreenshotToBitmap() throws Exception {
    Bitmap bitmap = Falcon.takeScreenshotBitmap(_activityRule.getActivity());

    assertThat(bitmap).isNotNull();

    assertThat(bitmap.getWidth()).isGreaterThan(SMALLEST_SCREEN_EVER);
    assertThat(bitmap.getHeight()).isGreaterThan(SMALLEST_SCREEN_EVER);
  }

  @Test
  public void takesCorrectScreenshotSize() {
    SampleActivity activity = _activityRule.getActivity();
    Bitmap bitmap = Falcon.takeScreenshotBitmap(activity);

    View decorView = activity.getWindow().getDecorView();
    assertThat(bitmap.getWidth()).isEqualTo(decorView.getWidth());
    assertThat(bitmap.getHeight()).isEqualTo(decorView.getHeight());
  }

  //endregion
}
