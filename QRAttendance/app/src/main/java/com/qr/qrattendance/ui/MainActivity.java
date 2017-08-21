package com.qr.qrattendance.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import com.qr.qrattendance.CustomViews.SignatureView;
import com.qr.qrattendance.Interfaces.IAttendaceMark;
import com.qr.qrattendance.R;
import com.qr.qrattendance.database.sqlite.DatabaseManager;
import com.qr.qrattendance.model.Group;
import com.qr.qrattendance.pdf_handler.PDF_Manger;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IAttendaceMark{
    private TableLayout mTableLayout;
    int count;
    SignatureView signatureView;
    TextView mShowConvasLable;
    LinearLayout mTableBelowPart;
    static Image image;
    Button savePDF;
    private static String FILE = "mnt/sdcard/invoice.pdf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mTableLayout=(TableLayout)findViewById(R.id.readings_tableLayout);
        count = 0;
        signatureView = (SignatureView) findViewById(R.id.sinatureView);
        mShowConvasLable = (TextView) findViewById(R.id.showCanvasLable_TextView);
        mTableBelowPart = (LinearLayout) findViewById(R.id.TableBlowPortion_linearLayout);
        savePDF = (Button) findViewById(R.id.savePDF_btn);
        savePDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConvertPDF();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.main_menu, menu);
        return (super.onCreateOptionsMenu(menu));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_attendace:
                Intent scanIntent = new Intent(MainActivity.this,ReadQRCodeActivity.class);
                startActivityForResult(scanIntent , 1);
                return true;
            case R.id.action_generate_qr:
                startActivity(new Intent(MainActivity.this,QRgenerateActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void AttendaceMark(String student) {  // interface method

        String[] stu_info_array = new String[]{};
        stu_info_array = student.split(";");
        String stu_name,stu_number,stu_badge;
        stu_name = stu_info_array[0];
        stu_number = stu_info_array[1];
        stu_badge = stu_info_array[2];
        markAttandace(stu_name,stu_number,stu_badge);
    }
    public void markAttandace(String name , String number , String badge){
        TableRow tableHeader = (TableRow) findViewById(R.id.table_header);
        tableHeader.setVisibility(View.VISIBLE);
        mTableBelowPart.setVisibility(View.VISIBLE);
        View tableRow = LayoutInflater.from(this).inflate(R.layout.table_row,null,false);
        TextView mCounter_textView  = (TextView) tableRow.findViewById(R.id.couter_textView);
        TextView   mName_textView= (TextView) tableRow.findViewById(R.id.student_name_textView);
        TextView mNumber_textView  = (TextView) tableRow.findViewById(R.id.student_number_textView);
        TextView mBadge_textView  = (TextView) tableRow.findViewById(R.id.student_badge_textView);
        mCounter_textView.setText(""+count++);
        mName_textView.setText(name);
        mNumber_textView.setText(number);
        mBadge_textView.setText(badge);
        mTableLayout.addView(tableRow);

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1)
        {
            if(resultCode == RESULT_OK) {
                String strEditText = data.getStringExtra("editTextValue");
                AttendaceMark(strEditText);
                Intent scanIntent = new Intent(MainActivity.this,ReadQRCodeActivity.class);
                startActivityForResult(scanIntent , 1);
            }
        }
    }
    public void ConVaseVisibility(View view){
        mShowConvasLable.setVisibility(View.INVISIBLE);
        signatureView.setVisibility(View.VISIBLE);

    }

    public void ConvertPDF(){
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        ScrollView root = (ScrollView) inflater.inflate(R.layout.activity_main, null); //RelativeLayout is root view of my UI(xml) file.
        root.setDrawingCacheEnabled(true);
        Bitmap screen= PDF_Manger.getBitmapFromView(findViewById(R.id.main_Scroll_root)); // here give id of our root layout (here its my RelativeLayout's id
        root.setDrawingCacheEnabled(false);
        PDF_Manger pdf_manger = new PDF_Manger();
        pdf_manger.makeDocument(screen);
    }


}
