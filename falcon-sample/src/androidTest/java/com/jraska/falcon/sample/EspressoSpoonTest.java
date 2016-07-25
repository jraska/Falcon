package com.jraska.falcon.sample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.jraska.falcon.FalconSpoon;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

/**
 * Example Espresso test of dialog taken by SpoonCompat
 */
@RunWith(AndroidJUnit4.class)
public class EspressoSpoonTest {

  //region Fields

  @Rule
  public ActivityTestRule<SampleActivity> _activityRule = new ActivityTestRule<>(
      SampleActivity.class);

  private List<File> takenScreenshots = new ArrayList<>();

  //endregion

  //region Setup Methods

  @After
  public void after() throws Exception {
    for (File screenshot : takenScreenshots) {
      assertThat(screenshot.delete(), is(true));
    }
  }

  //endregion


  //region Test Methods

  @Test
  public void dialogTakenInScreenshot() throws Exception {
    SampleActivity activity = _activityRule.getActivity();
    File screenshotWithoutDialogFile = FalconSpoon.screenshot(activity, "No_dialog");
    takenScreenshots.add(screenshotWithoutDialogFile);
    double screenHsvValueWithoutDialog = computeAverageHsvValue(screenshotWithoutDialogFile);

    onView(withId(R.id.show_dialog)).perform(click());
    onView(withText("Screenshot")).check(matches(isDisplayed()));

    File screenshotWithDialogFile = FalconSpoon.screenshot(activity, "Dialog_test");
    takenScreenshots.add(screenshotWithDialogFile);
    double screenHsvValueWithDialog = computeAverageHsvValue(screenshotWithDialogFile);

    // HSV Value fo screenshot without dialog should be higher, due to dimming around dialog
    double withoutScreenshotHsvRatio = screenHsvValueWithoutDialog / screenHsvValueWithDialog;
    String message = String.format("Dialog screen must be darker. Dialog value=%s, No dialog=%s",
        screenHsvValueWithDialog, screenHsvValueWithoutDialog);
    assertThat(message, withoutScreenshotHsvRatio, greaterThan(1.3));
  }

  //endregion

  //region Methods

  private static double computeAverageHsvValue(File bitmapFile) {
    BitmapFactory.Options sampleImageOptions = new BitmapFactory.Options();
    sampleImageOptions.inSampleSize = 4;

    Bitmap bitmap = BitmapFactory.decodeFile(bitmapFile.getAbsolutePath(), sampleImageOptions);
    return computeAverageHsvValue(bitmap);
  }

  private static double computeAverageHsvValue(Bitmap bitmap) {
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

  //endregion
}
