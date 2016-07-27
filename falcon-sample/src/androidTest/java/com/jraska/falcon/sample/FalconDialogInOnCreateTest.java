package com.jraska.falcon.sample;

import android.graphics.Bitmap;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import com.jraska.falcon.Falcon;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.jraska.falcon.sample.CICheck.assumeNoCI;
import static com.jraska.falcon.sample.asserts.BitmapAssert.assertThatBitmap;

public class FalconDialogInOnCreateTest {
  @Rule
  public ActivityTestRule<DialogOnCreate> _activityRule = new ActivityTestRule<>(
      DialogOnCreate.class);

  // Tests https://github.com/jraska/Falcon/issues/11
  @Test
  public void takesDialogOnCreate() {
    assumeNoCI();

    DialogOnCreate activity = _activityRule.getActivity();
    onView(withText(DialogOnCreate.DIALOG_TITLE)).check(matches(isDisplayed()));

    Bitmap withDialog = Falcon.takeScreenshotBitmap(activity);
    Espresso.pressBack();
    onView(withText(DialogOnCreate.DIALOG_TITLE)).check(doesNotExist());

    Bitmap afterDialogDismiss = Falcon.takeScreenshotBitmap(activity);

    assertThatBitmap(withDialog).isDarkerThan(afterDialogDismiss);
  }
}
