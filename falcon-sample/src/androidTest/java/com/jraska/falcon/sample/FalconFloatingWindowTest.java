package com.jraska.falcon.sample;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import com.jraska.falcon.Falcon;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.jraska.falcon.sample.CICheck.assumeNoCI;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(AndroidJUnit4.class)
public class FalconFloatingWindowTest {
  //region Fields

  @Rule
  public ActivityTestRule<FloatingViewActivity> _activityRule = new ActivityTestRule<>(
      FloatingViewActivity.class);

  //endregion

  //region Test methods

  @Test
  public void takesCorrectScreenshotSize() throws InterruptedException {
    waitForActivitiesToFinish();

    Activity activity = _activityRule.getActivity();

    Bitmap bitmap = Falcon.takeScreenshotBitmap(activity);

    View decorView = activity.getWindow().getDecorView();
    assertThat(bitmap.getWidth()).isEqualTo(decorView.getWidth());
    assertThat(bitmap.getHeight()).isEqualTo(decorView.getHeight());
  }

  @Test
  public void takesToastOutOfWindowIntoScreenshot() {
    assumeNoCI();

    SampleActivity activity = _activityRule.getActivity();
    onView(withId(R.id.show_toast)).perform(click());

    Bitmap bitmap = Falcon.takeScreenshotBitmap(activity);

    View decorView = activity.getWindow().getDecorView();
    assertThat(bitmap.getHeight()).isGreaterThan(decorView.getHeight());
  }

  //endregion

  //region Methods

  private static void waitForActivitiesToFinish() throws InterruptedException {
    // TODO: 26/07/16 Use idling resource to wait for all other activities to finish
    // Currently previous tests influence this test - Activities did not finish properly
    // and their windows are still attached and taken to screenshot.
    // Since they are bigger then floating window it ruins the test
    // Feel free to submit pull request with solution
    Thread.sleep(3000);
  }

  //endregion
}
