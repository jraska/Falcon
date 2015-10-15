package com.jraska.falcon.sample;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import com.jraska.falcon.FalconSpoon;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

/**
 * Shows usage of {@link FalconSpoon} compat screenshots
 */
@RunWith(AndroidJUnit4.class)
public class FalconSpoonTest extends ActivityInstrumentationTestCase2<SampleActivity> {
  //region Fields

  private File _screenshotFile;

  //endregion


  //region Constructors

  public FalconSpoonTest() {
    super(SampleActivity.class);
  }

  //endregion

  //region Setup Methods

  @Before
  @Override
  public void setUp() throws Exception {
    super.setUp();

    injectInstrumentation(InstrumentationRegistry.getInstrumentation());

    getActivity();
  }

  @SuppressWarnings("ResultOfMethodCallIgnored")
  @After
  @Override
  public void tearDown() throws Exception {
    if (_screenshotFile != null) {
      _screenshotFile.delete();
    }

    super.tearDown();
  }

  //endregion

  //region Test methods

  @Test
  public void testTakeScreenshot() throws Exception {
    String tag = "ExampleScreenshot";
    _screenshotFile = FalconSpoon.takeScreenshot(getActivity(), tag);

    assertThat(_screenshotFile.length(), greaterThan(0L));
  }

  @Test
  public void testTakeScreenshotWithCustomNames() throws Exception {
    String tag = "ExampleScreenshot";

    _screenshotFile = FalconSpoon.takeScreenshot(getActivity(), tag,
        "FalconSpponTest", "CustomMethodName");

    assertThat(_screenshotFile.length(), greaterThan(0L));
  }

  //endregion
}
