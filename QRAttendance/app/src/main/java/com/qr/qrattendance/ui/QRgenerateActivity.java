package com.qr.qrattendance.ui;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.qr.qrattendance.R;
import com.qr.qrattendance.qr_scanner.QRScannerHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class QRgenerateActivity extends AppCompatActivity
{
    EditText student_name_editText,student_number_editText,student_badge_editText;
    Button generate_qr_code_button;
    ImageView qr_code_imageView;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    File imagePath;
    String NameEditVale,BatchEditVale,NumberEditVale ;
    Thread thread ;
    public final static int QRcodeWidth = 500 ;
    Bitmap bitmap ;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrgenerate);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitle("QR Generator");
        qr_code_imageView = (ImageView)findViewById(R.id.qr_code_imageView);
        student_name_editText = (EditText)findViewById(R.id.name_edittext);
        student_badge_editText = (EditText)findViewById(R.id.bage_edittext);
        student_number_editText = (EditText)findViewById(R.id.number_edittext);
        generate_qr_code_button = (Button)findViewById(R.id.generate_qr_button);
        // Register Listener
        QRgeneratorButton_Handler();

    }
    public void QRgeneratorButton_Handler()
    {
        generate_qr_code_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                NameEditVale = student_name_editText.getText().toString();
                BatchEditVale = student_badge_editText.getText().toString();
                NumberEditVale = student_number_editText.getText().toString();
                String StringBuilding=NameEditVale+";"+BatchEditVale+";"+NumberEditVale;
                try {
                    bitmap = QRScannerHelper.TextToImageEncode(StringBuilding);
                    student_badge_editText.setText("");
                    student_name_editText.setText("");
                    student_number_editText.setText("");
                    qr_code_imageView.setImageBitmap(bitmap);
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable()   //pause excution 1000ms
                    {
                        @Override
                        public void run()
                        {
                            Bitmap bitmap = takeScreenshot();
                            saveBitmap(bitmap,NumberEditVale);
                        }
                    }, 1000);



                } catch (WriterException e)
                {
                    e.printStackTrace();
                }


            }

        });


    }
    public Bitmap takeScreenshot()
    {
        View rootView = findViewById(R.id.qr_code_imageView);
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }
    public void saveBitmap(Bitmap bitmap,String StudentNumber)
    {

        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Screenshots";
        File dir = new File(dirPath);
        if(!dir.exists())
            dir.mkdirs();
        File file = new File(dirPath, StudentNumber+".png");
        FileOutputStream fos;
        try
        {
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        }
        catch (FileNotFoundException e)
        {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e)
        {
            Log.e("GREC", e.getMessage(), e);
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
           onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }


}
