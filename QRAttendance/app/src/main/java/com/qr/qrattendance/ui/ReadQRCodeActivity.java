package com.qr.qrattendance.ui;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.Result;
import com.qr.qrattendance.Interfaces.IAttendaceMark;
import com.qr.qrattendance.R;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ReadQRCodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
  //  IAttendaceMark mMainActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view
      //  mMainActivity = new MainActivity();

    }

    @Override
    public void handleResult(Result result) {
     //   Toast.makeText(ReadQRCodeActivity.this,result.toString(),Toast.LENGTH_SHORT).show();
   //  mMainActivity.AttendaceMark(result.toString());
        // If you would like to resume scanning, call this method below:
        //mScannerView.resumeCameraPreview(this);

        String s = result.getText().toString();
        String[] array = new String[]{};
        array = s.split(";");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        builder.setMessage("Name  "+array[0]+" \n"+"Number  "+array[2]);
        AlertDialog alert1 = builder.create();
        alert1.show();

    }
    @Override
    protected void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }
}
