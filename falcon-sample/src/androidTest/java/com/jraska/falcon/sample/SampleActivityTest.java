package com.jraska.falcon.sample;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import com.jraska.falcon.Falcon;
import com.jraska.falcon.FalconSpoon;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

/**
 * Shows usage of {@link Falcon} and {@link FalconSpoon} screenshots
 */
@RunWith(AndroidJUnit4.class)
public class SampleActivityTest extends ActivityInstrumentationTestCase2<SampleActivity> {
  //region Fields

  private File _imageFile;

  //endregion

  //region Constructors

  public SampleActivityTest() {
    super(SampleActivity.class);
  }

  //endregion

  //region Setup Methods

  @Before
  @Override
  public void setUp() throws Exception {
    super.setUp();

    injectInstrumentation(InstrumentationRegistry.getInstrumentation());

    // Launches the activity
    getActivity();
  }

  @SuppressWarnings("ResultOfMethodCallIgnored")
  @After
  @Override
  public void tearDown() throws Exception {
    _imageFile.delete();

    super.tearDown();
  }

  //endregion

  //region Test methods

  @Test
  public void testSavesFalconFile() throws Exception {
    File newFile = getActivity().getScreenshotFile();
    _imageFile = newFile;

    //check that file does not exist yet
    assertFalse(newFile.exists());

    Falcon.takeScreenshot(getActivity(), newFile);

    assertThat(newFile.length(), greaterThan(0L));
  }

  //endregion
}
