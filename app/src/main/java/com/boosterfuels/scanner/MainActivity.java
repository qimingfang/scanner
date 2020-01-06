package com.boosterfuels.scanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

public class MainActivity extends AppCompatActivity {

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
    mCodeScanner.startPreview();
  }

  @Override
  protected void onPause() {
    mCodeScanner.releaseResources();
    super.onPause();
  }
}
