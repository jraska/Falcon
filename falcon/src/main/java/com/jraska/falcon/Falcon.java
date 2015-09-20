package com.jraska.falcon;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager.LayoutParams;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Falcon {
  //region Constants

  private static final String TAG = "Falcon";

  //endregion

  //region Public API

  public static File takeScreenshot(Activity activity, final File toFile) {
    Bitmap bitmap = takeScreenshotBitmap(activity);

    try {
      writeBitmap(bitmap, toFile);
    }
    catch (IOException e) {
      throw new RuntimeException(e); // TODO(20.9.2015): Error handling
    }
    finally {
      bitmap.recycle();
    }

    return toFile;
  }

  public static Bitmap takeScreenshotBitmap(Activity activity) {
    final List<ViewRootData> viewRoots = getRootViews(activity);
    View main = activity.getWindow().getDecorView();

    final Bitmap bitmap = Bitmap.createBitmap(main.getWidth(), main.getHeight(), Bitmap.Config.ARGB_8888);

    // We need to do it in main thread
    if (Looper.myLooper() == Looper.getMainLooper()) {
      drawRootsToBitmap(viewRoots, bitmap);
    } else {
      final CountDownLatch latch = new CountDownLatch(1);
      activity.runOnUiThread(new Runnable() {
        @Override
        public void run() {
          try {
            drawRootsToBitmap(viewRoots, bitmap);
          }
          finally {
            latch.countDown();
          }
        }
      });
      try {
        latch.await();
      }
      catch (InterruptedException e) {
        Log.e(TAG, "Unable to get screenshot", e);
        throw new RuntimeException(e);
      }
    }

    return bitmap;
  }

  //endregion

  //region Methods

  private static void drawRootsToBitmap(List<ViewRootData> viewRoots, Bitmap bitmap) {
    for (ViewRootData rootData : viewRoots) {
      drawRootToBitmap(rootData, bitmap);
    }
  }

  private static void drawRootToBitmap(ViewRootData config, Bitmap bitmap) {
    // now only dim supported
    if ((config._layoutParams.flags & LayoutParams.FLAG_DIM_BEHIND) == LayoutParams.FLAG_DIM_BEHIND) {
      Canvas dimCanvas = new Canvas(bitmap);
      dimCanvas.drawARGB((int) (255 * config._layoutParams.dimAmount), 0, 0, 0);
    }

    Canvas canvas = new Canvas(bitmap);
    canvas.translate(config._winFrame.left, config._winFrame.top);
    config._view.draw(canvas);
  }

  private static void writeBitmap(Bitmap bitmap, File toFile) throws IOException {
    OutputStream outputStream = null;
    try {
      outputStream = new BufferedOutputStream(new FileOutputStream(toFile));
      bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
    }
    finally {
      if (outputStream != null) {
        outputStream.close();
      }
    }
  }

  @SuppressWarnings("unchecked") // no way to check
  private static List<ViewRootData> getRootViews(Activity activity) {
    List<ViewRootData> rootViews = new ArrayList<>();

    Object globalWindowManager = getFieldValue("mGlobal", activity.getWindowManager());
    Object rootObjects = getFieldValue("mRoots", globalWindowManager);
    Object paramsObject = getFieldValue("mParams", globalWindowManager);

    Object[] roots;
    LayoutParams[] params;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      roots = ((List) rootObjects).toArray();

      List<LayoutParams> paramsList = (List<LayoutParams>) paramsObject;
      params = paramsList.toArray(new LayoutParams[paramsList.size()]);
    } else {
      roots = (Object[]) rootObjects;
      params = (LayoutParams[]) paramsObject;
    }

    for (int i = 0; i < roots.length; i++) {
      Object root = roots[i];

      Rect area = (Rect) getFieldValue("mWinFrame", root);
      View view = (View) getFieldValue("mView", root);
      rootViews.add(new ViewRootData(view, area, params[i]));
    }

    return rootViews;
  }

  private static Object getFieldValue(String fieldName, Object target) {
    try {
      return getFieldValueUnchecked(fieldName, target);
    }
    catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private static Object getFieldValueUnchecked(String fieldName, Object target)
      throws NoSuchFieldException, IllegalAccessException {
    // Here no recursion to upper classes all fields we need are directly declared by provided classes
    Field field = target.getClass().getDeclaredField(fieldName);

    field.setAccessible(true);
    return field.get(target);
  }

  //endregion

  //region Nested classes

  private static class ViewRootData {
    private final View _view;
    private final Rect _winFrame;
    private final LayoutParams _layoutParams;

    public ViewRootData(View view, Rect winFrame, LayoutParams layoutParams) {
      _view = view;
      _winFrame = winFrame;
      _layoutParams = layoutParams;
    }
  }

  //endregion
}
