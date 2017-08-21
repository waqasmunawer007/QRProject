package com.qr.qrattendance.ui;

import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.qr.qrattendance.CustomViews.SignatureView;
import com.qr.qrattendance.R;

import java.io.FileOutputStream;

public class SignatureActivty extends AppCompatActivity {
  Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature_activty);
        Bitmap bmp = ((SignatureView)findViewById(R.id.sinatureView)).getImage();
        btn = (Button) findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bmp = ((SignatureView)findViewById(R.id.sinatureView)).getImage();
                saveBitmap(bmp);
            }
        });
    }
    public void saveBitmap(Bitmap bmp) {
        try {
            String root = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
            String filepath = root + "signature.jpg";

            FileOutputStream fos = new FileOutputStream(filepath);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        }
        catch(Exception e) {
            Log.e("Could not save", e.getMessage());
            e.printStackTrace();
        }
    }
}
