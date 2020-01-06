package com.boosterfuels.scanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

public class MainActivity extends AppCompatActivity {

  public static final int MY_CAMERA_REQUEST_CODE = 1010;

  private CodeScanner mCodeScanner;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    CodeScannerView scannerView = findViewById(R.id.scanner_view);

    final Activity activity = this;

    mCodeScanner = new CodeScanner(this, scannerView);
    mCodeScanner.setDecodeCallback(new DecodeCallback() {
      @Override
      public void onDecoded(@NonNull final Result result) {
        activity.runOnUiThread(new Runnable() {
          @Override
          public void run() {
            Toast.makeText(activity, result.getText(), Toast.LENGTH_SHORT).show();
          }
        });
      }
    });

    scannerView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        mCodeScanner.startPreview();
      }
    });
  }

  @Override
  protected void onResume() {
    super.onResume();

    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
      == PackageManager.PERMISSION_DENIED) {
      ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA},
        MY_CAMERA_REQUEST_CODE);
    } else {
      mCodeScanner.startPreview();
    }
  }

  @Override
  protected void onPause() {
    mCodeScanner.releaseResources();
    super.onPause();
  }
}
