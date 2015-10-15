package com.jraska.falcon.sample.matchers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.io.File;

/**
 * Utility class to check if the file is bitmap
 */
public class BitmapFileMatcher extends TypeSafeMatcher<File> implements Matcher<File> {

  //region Matcher impl

  @Override
  protected boolean matchesSafely(File item) {
    Bitmap bitmap = BitmapFactory.decodeFile(item.getAbsolutePath());

    boolean matches = bitmap != null && bitmap.getWidth() > 0 && bitmap.getHeight() > 0;

    if (bitmap != null) {
      bitmap.recycle();
    }

    return matches;
  }

  @Override
  public void describeTo(Description description) {
    description.appendText("The file is Bitmap");
  }

  @Override
  protected void describeMismatchSafely(File item, Description mismatchDescription) {
    mismatchDescription.appendText(" file ").appendText(item.getAbsolutePath());
    mismatchDescription.appendText(" was not decoded as Bitmap");
  }

  //endregion

  //region Methods

  public static Matcher<File> isBitmap() {
    return new BitmapFileMatcher();
  }

  //endregion
}
