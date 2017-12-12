package com.jraska.falcon.sample;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.jraska.falcon.FalconSpoonRule;
import com.squareup.spoon.SpoonRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import static com.jraska.falcon.sample.Assumptions.assumeSpoonPermissions;
import static com.jraska.falcon.sample.asserts.BitmapFileAssert.assertThatFile;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Shows usage of {@link FalconSpoonRule} compat screenshots
 */
@RunWith(AndroidJUnit4.class)
public class FalconSpoonTest {

  //region Fields

  @Rule
  public ActivityTestRule<SampleActivity> _activityRule = new ActivityTestRule<>(
      SampleActivity.class);

  @Rule
  public final SpoonRule _spoonRule = new SpoonRule();

  private File _screenshotFile;

  //endregion

  //region Setup Methods

  @Before
  public void before() throws Exception {
    assumeSpoonPermissions();
  }

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
    String tag = "ExampleScreenshot";
    _screenshotFile = FalconSpoonRule.screenshot(_spoonRule, _activityRule.getActivity(), tag);

    assertThat(_screenshotFile.length()).isGreaterThan(0L);
    assertThatFile(_screenshotFile).isBitmap();
  }

  //endregion
}
