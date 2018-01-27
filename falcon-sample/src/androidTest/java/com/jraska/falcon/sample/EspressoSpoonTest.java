package com.jraska.falcon.sample;

import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;
import com.jraska.falcon.FalconSpoonRule;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static android.support.test.rule.GrantPermissionRule.grant;
import static com.jraska.falcon.sample.asserts.BitmapFileAssert.assertThatFile;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Example Espresso test of dialog taken by SpoonCompat
 */
@RunWith(AndroidJUnit4.class)
public class EspressoSpoonTest {

  //region Fields

  @Rule
  public ActivityTestRule<SampleActivity> _activityRule = new ActivityTestRule<>(
      SampleActivity.class);

  @Rule
  public final FalconSpoonRule _falconSpoonRule = new FalconSpoonRule();

  @Rule
  public final GrantPermissionRule permissionRule = grant(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE);

  private List<File> takenScreenshots = new ArrayList<>();

  //endregion

  //region Setup Methods

  @After
  public void after() throws Exception {
    for (File screenshot : takenScreenshots) {
      assertThat(screenshot.delete()).isTrue();
    }
  }

  //endregion

  //region Test Methods

  @Test
  public void dialogTakenInScreenshot() throws Exception {
    SampleActivity activity = _activityRule.getActivity();

    File screenshotWithoutDialogFile = _falconSpoonRule.screenshot(activity, "No_dialog");
    takenScreenshots.add(screenshotWithoutDialogFile);

    onView(withId(R.id.show_dialog)).perform(click());
    onView(withText("Screenshot")).check(matches(isDisplayed()));

    File screenshotWithDialogFile = _falconSpoonRule.screenshot(activity, "Dialog_test");
    takenScreenshots.add(screenshotWithDialogFile);

    assertThatFile(screenshotWithDialogFile).isDarkerThan(screenshotWithoutDialogFile);
  }

  //endregion
}
