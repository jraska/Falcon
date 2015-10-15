package com.jraska.falcon.sample;

import android.graphics.Bitmap;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import com.jraska.falcon.Falcon;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import static com.jraska.falcon.sample.matchers.BitmapFileMatcher.isBitmap;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

/**
 * Shows usage of {@link Falcon} screenshots
 */
@RunWith(AndroidJUnit4.class)
public class FalconTest extends ActivityInstrumentationTestCase2<SampleActivity> {
  //region Constants

  public static final int SMALLEST_SCREEN_EVER = 100;

  //endregion

  //region Fields

  private File _screenshotFile;

  //endregion

  //region Constructors

  public FalconTest() {
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
    File newFile = getActivity().getScreenshotFile();
    _screenshotFile = newFile;

    //check that file does not exist yet
    assertFalse(newFile.exists());

    Falcon.takeScreenshot(getActivity(), newFile);

    assertThat(newFile, isBitmap());
  }

  @Test
  public void testTakeScreenshotBitmap() throws Exception {
    Bitmap bitmap = Falcon.takeScreenshotBitmap(getActivity());

    assertThat(bitmap, not(nullValue()));

    assertThat(bitmap.getWidth(), greaterThan(SMALLEST_SCREEN_EVER));
    assertThat(bitmap.getHeight(), greaterThan(SMALLEST_SCREEN_EVER));
  }

  //endregion
}
