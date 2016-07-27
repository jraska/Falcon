package com.jraska.falcon.sample.asserts;

import android.graphics.Bitmap;
import android.graphics.Color;
import org.assertj.core.api.AbstractAssert;

public final class BitmapAssert extends AbstractAssert<BitmapAssert, Bitmap> {
  public BitmapAssert(Bitmap actual) {
    super(actual, BitmapAssert.class);
  }

  public static BitmapAssert assertThatBitmap(Bitmap bitmap) {
    return new BitmapAssert(bitmap);
  }

  public BitmapAssert hasWidth(int expectedWidth) {
    isNotNull();
    if (actual.getWidth() != expectedWidth) {
      failWithMessage("Expected bitmap width to be <%s> but was <%s>", expectedWidth, actual.getWidth());
    }

    return this;
  }

  public BitmapAssert hasWidthGreaterThan(int expectedWidth) {
    isNotNull();
    if (actual.getWidth() <= expectedWidth) {
      failWithMessage("Expected bitmap width to be  greater than <%s> but was <%s>", expectedWidth, actual.getWidth());
    }

    return this;
  }

  public BitmapAssert hasHeight(int expectedHeight) {
    isNotNull();
    if (actual.getHeight() != expectedHeight) {
      failWithMessage("Expected bitmap height to be <%s> but was <%s>", expectedHeight, actual.getHeight());
    }

    return this;
  }

  public BitmapAssert hasHeightGreaterThan(int expectedHeight) {
    isNotNull();
    if (actual.getHeight() <= expectedHeight) {
      failWithMessage("Expected bitmap height to be greater then <%s> but was <%s>", expectedHeight, actual.getHeight());
    }

    return this;
  }

  public BitmapAssert isDarkerThan(Bitmap bitmap) {
    return isDarkerThanByRatio(bitmap, 1.001);
  }

  public BitmapAssert isSameAs(Bitmap bitmap) {
    double averageHsvValue = computeAverageHsvValue(bitmap);
    double actualHsvValue = computeAverageHsvValue(actual);

    if (Double.compare(averageHsvValue, actualHsvValue) != 0) {
      failWithMessage("Bitmap is not same as actual");
    }

    return this;
  }

  public BitmapAssert isDifferentThan(Bitmap bitmap) {
    double averageHsvValue = computeAverageHsvValue(bitmap);
    double actualHsvValue = computeAverageHsvValue(actual);

    if (Double.compare(averageHsvValue, actualHsvValue) == 0) {
      failWithMessage("Bitmap is same as actual");
    }

    return this;
  }

  public BitmapAssert isDarkerThanByRatio(Bitmap bitmap, double ratio) {
    double averageHsvValue = computeAverageHsvValue(bitmap);
    double actualHsvValue = computeAverageHsvValue(actual);

    double valueRatio = averageHsvValue / actualHsvValue;
    if (valueRatio < ratio) {
      failWithMessage("Bitmap was expected to be darker then by ratio at least<%s>, but ratio was <%s>",
          ratio, valueRatio);
    }

    return this;
  }

  static double computeAverageHsvValue(Bitmap bitmap) {
    int[] pixelColors = new int[bitmap.getWidth() * bitmap.getHeight()];
    bitmap.getPixels(pixelColors, 0, bitmap.getWidth(), 0, 0,
        bitmap.getWidth(), bitmap.getHeight());

    double sum = 0;
    float[] hsv = new float[3];
    int step = decideBitmapTraversalStep(bitmap);

    for (int i = 0, length = pixelColors.length; i < length; i += step) {
      Color.colorToHSV(pixelColors[i], hsv);

      sum += hsv[2];
    }

    return sum * step / pixelColors.length;
  }

  private static int decideBitmapTraversalStep(Bitmap bitmap) {
    int countOfPixels = bitmap.getHeight() * bitmap.getWidth();

    if (countOfPixels < 640 * 480) {
      return 1;
    } else if (countOfPixels < 1024 * 768) {
      return 2;
    } else if (countOfPixels < 1600 * 1200) {
      return 4;
    } else {
      return 8;
    }
  }
}
