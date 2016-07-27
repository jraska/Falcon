package com.jraska.falcon.sample.asserts;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import org.assertj.core.api.FileAssert;

import java.io.File;

import static com.jraska.falcon.sample.asserts.BitmapAssert.assertThatBitmap;

public final class BitmapFileAssert extends FileAssert {
  private BitmapFileAssert(File actual) {
    super(actual);
  }

  public static BitmapFileAssert assertThatFile(File file) {
    return new BitmapFileAssert(file);
  }

  public BitmapFileAssert isBitmap() {
    isNotNull();

    Bitmap bitmap = BitmapFactory.decodeFile(actual.getAbsolutePath());
    boolean isBitmap = bitmap != null && bitmap.getWidth() > 0 && bitmap.getHeight() > 0;

    if (bitmap != null) {
      bitmap.recycle();
    }

    if (!isBitmap) {
      failWithMessage("File <%s> was expected to be valid Bitmap", actual);
    }

    return this;
  }

  public BitmapFileAssert isDarkerThan(File bitmapFile) {
    Bitmap actualBitmap = loadSampledBitmap(actual);
    Bitmap bitmap = loadSampledBitmap(bitmapFile);

    assertThatBitmap(actualBitmap).isDarkerThan(bitmap);

    return this;
  }

  static double computeAverageHsvValue(File bitmapFile) {
    Bitmap bitmap = loadSampledBitmap(bitmapFile);
    return BitmapAssert.computeAverageHsvValue(bitmap);
  }

  private static Bitmap loadSampledBitmap(File bitmapFile) {
    BitmapFactory.Options sampleImageOptions = new BitmapFactory.Options();
    sampleImageOptions.inSampleSize = 4;

    return BitmapFactory.decodeFile(bitmapFile.getAbsolutePath(), sampleImageOptions);
  }
}
