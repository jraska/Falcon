package com.jraska.falcon;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;

import com.jraska.falcon.sample.SampleActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(AndroidJUnit4.class)
public class FalconThreadingTest {
  @Rule
  public ActivityTestRule<SampleActivity> _activityRule = new ActivityTestRule<>(
    SampleActivity.class);

  @Test
  public void screenshotFromOtherThreadWorks() throws InterruptedException {
    SampleActivity activity = _activityRule.getActivity();

    CountDownLatch countDownLatch = new CountDownLatch(1);
    TakeScreenShotRunnable takeScreenShotRunnable = new TakeScreenShotRunnable(activity, countDownLatch);
    new Thread(takeScreenShotRunnable).run();

    boolean await = countDownLatch.await(10L, TimeUnit.SECONDS);
    assertThat(await).isTrue();
    assertThat(takeScreenShotRunnable._bitmap).isNotNull();
  }

  @Test(expected = Falcon.UnableToTakeScreenshotException.class)
  public void crashesWithUnableToTakeScreenshoExceptionWhenWrongView() throws Exception {
    SampleActivity activity = _activityRule.getActivity();

    View view = Falcon.getRootViews(activity).get(0)._view.findViewById(android.R.id.content);

    Field childrenField = Falcon.findField("mChildren", ViewGroup.class);
    childrenField.setAccessible(true);
    Object children = childrenField.get(view);
    childrenField.set(view, null);

    try {
      Falcon.takeScreenshotBitmap(activity);
    } finally {
      childrenField.set(view, children);
    }
  }

  static class TakeScreenShotRunnable implements Runnable {
    private final Activity _activity;
    private final CountDownLatch _latch;

    Bitmap _bitmap;

    TakeScreenShotRunnable(Activity activity, CountDownLatch latch) {
      _activity = activity;
      _latch = latch;
    }

    @Override
    public void run() {
      try {
        _bitmap = Falcon.takeScreenshotBitmap(_activity);
      } finally {
        _latch.countDown();
      }
    }
  }
}
