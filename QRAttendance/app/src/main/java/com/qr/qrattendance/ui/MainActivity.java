package com.qr.qrattendance.ui;

import android.content.Intent;
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
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.qr.qrattendance.Interfaces.IAttendaceMark;
import com.qr.qrattendance.R;
import com.qr.qrattendance.database.sqlite.DatabaseManager;
import com.qr.qrattendance.model.Group;

import java.util.List;

public class MainActivity extends AppCompatActivity implements IAttendaceMark{
    private TableLayout mTableLayout;
    int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mTableLayout=(TableLayout)findViewById(R.id.readings_tableLayout);
        count = 0;




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
                startActivity(new Intent(MainActivity.this,ReadQRCodeActivity.class));
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
}
