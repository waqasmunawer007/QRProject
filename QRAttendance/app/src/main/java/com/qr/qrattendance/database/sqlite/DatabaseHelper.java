package com.qr.qrattendance.database.sqlite;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.qr.qrattendance.app.QRAttendanceApp;
import com.qr.qrattendance.database.dao.StudentGroupTable;
import com.qr.qrattendance.model.Group;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by waqas on 8/15/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Name
    public static final String DATABASE_NAME = "qrAttendanceDB.db";
    /*version number to upgrade database version
    each time if you Add, Edit table, you need to change the
    version number. */
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper() {
        super(QRAttendanceApp.getAppContext(), QRAttendanceApp.getAppContext().getExternalFilesDir(null).getAbsolutePath() + "/" + DATABASE_NAME, null, DATABASE_VERSION);
        //if sd card path exist store db file on it otherwise use internal storage
//        String dbPath = Utils.getStorageDirectory() +DATABASE_NAME;
        getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(new StudentGroupTable().createTable());
//        db.execSQL(new DeviceParamsTable().createTable());
        seedData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + StudentGroupTable.TABLE_NAME);
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DeviceParamsTable.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
    private void seedData(SQLiteDatabase db) {
        insertGroups(db);
    }
    private void insertGroups(SQLiteDatabase db) {
        List<Group> groupList = new ArrayList<Group>();
        Group group1 = new Group();
        group1.setGroupName("G1");
        groupList.add(group1);

        Group group2 = new Group();
        group2.setGroupName("G2");
        groupList.add(group2);

        Group group3 = new Group();
        group3.setGroupName("G3");
        groupList.add(group3);

        for (Group group :
                groupList) {
            ContentValues values = new ContentValues();
            values.put(StudentGroupTable.COLUMN_GROUP_NAME, group.getGroupName());
            db.insert(StudentGroupTable.TABLE_NAME, null, values);
        }
    }

}