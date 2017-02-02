package com.jraska.falcon.sample;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.jraska.falcon.Falcon;
import com.jraska.falcon.sample.asserts.BitmapAssert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

@RunWith(AndroidJUnit4.class)
@RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class ActivitiesBelowNotIncludedTest {
  @Rule
  public ActivityTestRule<SampleActivity> _activityRule = new ActivityTestRule<>(
      SampleActivity.class);

  @Before
  public void before() {
    Assume.assumeThat(Build.VERSION.SDK_INT, greaterThanOrEqualTo(Build.VERSION_CODES.ICE_CREAM_SANDWICH));
  }

  @Test
  public void activityBelowNotIncluded() throws InterruptedException {

    Bitmap originalScreen = Falcon.takeScreenshotBitmap(_activityRule.getActivity());

    GetTopAcitivtyCallbacks callbacks = new GetTopAcitivtyCallbacks();
    _activityRule.getActivity().getApplication().registerActivityLifecycleCallbacks(callbacks);

    // starts new Activity
    onView(withId(R.id.toolbar)).perform(longClick());

    rotateScreen(callbacks._topActivity);
    Thread.sleep(1000); // Lame! Some idling resource should be used but for what to wait???

    Bitmap bitmap = Falcon.takeScreenshotBitmap(callbacks._topActivity);
    BitmapAssert.assertThatBitmap(bitmap).isRotatedSize(originalScreen);
  }

  private void rotateScreen(Activity activity) {
    Context context = InstrumentationRegistry.getTargetContext();
    int orientation = context.getResources().getConfiguration().orientation;

    activity.setRequestedOrientation(
        (orientation == Configuration.ORIENTATION_PORTRAIT) ?
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE : ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
  }

  static class GetTopAcitivtyCallbacks implements Application.ActivityLifecycleCallbacks {

    private Activity _topActivity;

    @Override public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
      _topActivity = activity;
    }

    @Override public void onActivityStarted(Activity activity) {
    }

    @Override public void onActivityResumed(Activity activity) {
      _topActivity = activity;
    }

    @Override public void onActivityPaused(Activity activity) {
    }

    @Override public void onActivityStopped(Activity activity) {
    }

    @Override public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    @Override public void onActivityDestroyed(Activity activity) {
    }
  }
}
