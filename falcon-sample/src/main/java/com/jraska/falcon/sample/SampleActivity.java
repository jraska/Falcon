package com.jraska.falcon.sample;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.jraska.falcon.Falcon;

import java.io.File;

public class SampleActivity extends AppCompatActivity {

  //region Activity overrides

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sample);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    ButterKnife.bind(this);
  }

  //endregion

  //region Methods

  @OnClick(R.id.show_snack)
  public void showSnackBar(View view) {
    Snackbar.make(view, "Snackbar", Snackbar.LENGTH_LONG)
        .setAction("Screenshot", new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            takeScreenshot();
          }
        }).show();
  }

  @OnClick(R.id.show_toast)
  public void showToast() {
    Toast.makeText(this, "Toast", Toast.LENGTH_LONG).show();
  }

  @OnClick(R.id.show_dialog)
  public void showDialog(View v) {

    //show all to have possibility see it in dialog screenshot
    showToast();
    showSnackBar(v);

    AlertDialog.Builder builder = new AlertDialog.Builder(SampleActivity.this);
    builder.setTitle("Falcon");
    builder.setMessage("Click button below to take screenshot with dialog");
    builder.setPositiveButton("Screenshot", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        takeScreenshot();
      }
    });
    builder.show();
  }

  @OnClick(R.id.fab_screenshot)
  public void takeScreenshot() {
    File screenshotFile = getScreenshotFile();
    Falcon.takeScreenshot(this, screenshotFile);

    String message = "Screenshot captured to " + screenshotFile.getAbsolutePath();
    Toast.makeText(this, message, Toast.LENGTH_LONG).show();
  }

  private File getScreenshotFile() {
    File screenshotDirectory;
    try {
      screenshotDirectory = getScreenshotsDirectory(getApplicationContext());
    }
    catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }

    String screenshotName = System.currentTimeMillis() + ".png";
    return new File(screenshotDirectory, screenshotName);
  }

  private static File getScreenshotsDirectory(Context context) throws IllegalAccessException {
    String dirName = "screenshots_ " + context.getPackageName();
    File directory = new File(Environment.getExternalStorageDirectory(), dirName);

    if (!directory.exists()) {
      if (!directory.mkdirs()) {
        throw new IllegalAccessException("Unable to create screenshot directory " + directory.getAbsolutePath());
      }
    }

    return directory;
  }

  //endregion
}
