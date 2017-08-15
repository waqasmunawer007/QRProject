package com.qr.qrattendance.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.qr.qrattendance.R;
import com.qr.qrattendance.database.sqlite.DatabaseManager;
import com.qr.qrattendance.model.Group;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         List<Group> groups = DatabaseManager.getInstance().getAllGroups();
    }
}
