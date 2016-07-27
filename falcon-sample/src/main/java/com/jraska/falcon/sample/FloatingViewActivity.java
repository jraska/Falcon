package com.jraska.falcon.sample;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;

public class FloatingViewActivity extends SampleActivity {
  @Override protected void onCreate(Bundle savedInstanceState) {
    setupFloatingWindow();

    super.onCreate(savedInstanceState);
  }

  private void setupFloatingWindow() {
    Point size = getSize();

    WindowManager.LayoutParams params = getWindow().getAttributes();
    params.width = size.x - 100;
    params.height = size.y / 2;
    params.alpha = 1;
    params.dimAmount = 0.4f;
    params.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
    getWindow().setAttributes(params);
  }

  @SuppressWarnings("deprecation") private Point getSize() {
    Point size = new Point();

    Display defaultDisplay = getWindow().getWindowManager().getDefaultDisplay();
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
      defaultDisplay.getSize(size);
    } else {
      size.x = defaultDisplay.getWidth();
      size.y = defaultDisplay.getHeight();
    }

    return size;
  }

  static void start(Activity from) {
    Intent intent = new Intent(from, FloatingViewActivity.class);
    from.startActivity(intent);
  }
}
