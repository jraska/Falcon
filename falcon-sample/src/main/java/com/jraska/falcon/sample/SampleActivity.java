package com.jraska.falcon.sample;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.jraska.falcon.Falcon;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SampleActivity extends AppCompatActivity {

  //region Fields

  @Bind(R.id.toolbar) Toolbar _toolbar;

  //endregion

  //region Activity overrides

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_sample);
    ButterKnife.bind(this);

    setSupportActionBar(_toolbar);
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

    // We are not in Unit test sot teh app
    File screenshotFile = getScreenshotFile();

    Falcon.takeScreenshot(this, screenshotFile);

    String message = "Screenshot captured to " + screenshotFile.getAbsolutePath();
    Toast.makeText(this, message, Toast.LENGTH_LONG).show();
  }

  @OnClick(R.id.show_popup)
  public void showPopup() {
    ListPopupWindow listPopupWindow = new ListPopupWindow(this, null);

    String[] data = {"Item 1", "Item 2", "Item 3"};
    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, data);
    listPopupWindow.setAdapter(adapter);

    listPopupWindow.setAnchorView(findViewById(R.id.show_popup));
    listPopupWindow.show();
  }

  protected File getScreenshotFile() {
    File screenshotDirectory;
    try {
      screenshotDirectory = getScreenshotsDirectory(getApplicationContext());
    }
    catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss.SSS", Locale.getDefault());

    String screenshotName = dateFormat.format(new Date()) + ".png";
    return new File(screenshotDirectory, screenshotName);
  }

  private static File getScreenshotsDirectory(Context context) throws IllegalAccessException {
    String dirName = "screenshots_" + context.getPackageName();

    File rootDir;

    if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
      rootDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
    } else {
      rootDir = context.getDir("screens", MODE_PRIVATE);
    }

    File directory = new File(rootDir, dirName);

    if (!directory.exists()) {
      if (!directory.mkdirs()) {
        throw new IllegalAccessException("Unable to create screenshot directory " + directory.getAbsolutePath());
      }
    }

    return directory;
  }

  //endregion
}
