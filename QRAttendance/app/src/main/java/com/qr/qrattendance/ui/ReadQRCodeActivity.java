package com.qr.qrattendance.ui;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view

    }
    @Override
    public void handleResult(Result result)
    {

        Intent intent = new Intent();
        intent.putExtra("editTextValue", result.toString());
        setResult(RESULT_OK, intent);    // send back (to Main Activty) Scanned Record
         finish();
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

    @Override
    protected void onStart() {
        super.onStart();

    }
}
