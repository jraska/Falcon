package com.jraska.falcon.sample;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

public class DialogOnCreate extends AppCompatActivity {
  public static final String DIALOG_TITLE = "Title";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_dialog_on_create);

    new AlertDialog.Builder(this)
        .setTitle(DIALOG_TITLE)
        .show();
  }
}
