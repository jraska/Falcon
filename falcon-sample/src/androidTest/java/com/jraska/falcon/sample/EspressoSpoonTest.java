package com.jraska.falcon.sample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import com.jraska.falcon.FalconSpoon;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;

@RunWith(AndroidJUnit4.class)
public class EspressoSpoonTest extends ActivityInstrumentationTestCase2<SampleActivity> {
  public EspressoSpoonTest() {
    super(SampleActivity.class);
  }

  private List<File> takenScreenshots = new ArrayList<>();

  @Before
  public void before() {
    injectInstrumentation(InstrumentationRegistry.getInstrumentation());
    getActivity();
  }

  @After
  public void after() throws Exception {
    for (File screenshot : takenScreenshots) {
      Assertions.assertThat(screenshot.delete()).isTrue();
    }
  }

  @Test
  public void testDialogTakenInScreenshot() throws Exception {
    File screenshotWithoutDialogFile = FalconSpoon.screenshot(getActivity(), "No_dialog");
    takenScreenshots.add(screenshotWithoutDialogFile);
    double screenHsvValueWithoutDialog = computeAverageHsvValue(screenshotWithoutDialogFile);


    onView(withId(R.id.show_dialog)).perform(click());
    onView(withText("Screenshot")).check(matches(isDisplayed()));

    File screenshotWithDialogFile = FalconSpoon.screenshot(getActivity(), "Dialog_test");
    takenScreenshots.add(screenshotWithDialogFile);
    double screenHsvValueWithDialog = computeAverageHsvValue(screenshotWithDialogFile);

    //HSV value fo screenshot without dialog should be much higher, due to dimming around dialog
    double withoutScreenshotHsvRatio = screenHsvValueWithoutDialog / screenHsvValueWithDialog;
    Assertions.assertThat(withoutScreenshotHsvRatio).isGreaterThan(1.5);
  }

  private double computeAverageHsvValue(File bitmapFile) {
    BitmapFactory.Options sampleImageOptions = new BitmapFactory.Options();
    sampleImageOptions.inSampleSize = 4;

    Bitmap bitmap = BitmapFactory.decodeFile(bitmapFile.getAbsolutePath(), sampleImageOptions);
    return computeAverageHsvValue(bitmap);
  }

  private double computeAverageHsvValue(Bitmap bitmap) {
    int[] pixelColors = new int[bitmap.getWidth() * bitmap.getHeight()];
    bitmap.getPixels(pixelColors, 0, bitmap.getWidth(), 0, 0,
        bitmap.getWidth(), bitmap.getHeight());

    double sum = 0;
    float[] hsv = new float[3];
    for (int pixelColor : pixelColors) {
      Color.colorToHSV(pixelColor, hsv);

      sum += hsv[2];
    }

    return sum / pixelColors.length;
  }
}
