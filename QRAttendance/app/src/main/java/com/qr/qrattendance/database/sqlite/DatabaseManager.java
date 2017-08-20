package com.qr.qrattendance.database.sqlite;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.qr.qrattendance.database.dao.StudentGroupTable;
import com.qr.qrattendance.model.Group;

import java.util.ArrayList;

/**
 * Created by waqas on 8/15/17.
 */

public class DatabaseManager {
    private static DatabaseManager databaseManager;
    private static SQLiteOpenHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    private DatabaseManager() {}

    public static synchronized DatabaseManager getInstance() {
        if (databaseManager == null) {
            databaseManager = new DatabaseManager();
            databaseHelper = new DatabaseHelper();
        }
        return databaseManager;
    }

    public synchronized SQLiteDatabase openDatabase() {
        // Opening new database
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            sqLiteDatabase = databaseHelper.getWritableDatabase();
        }
        return sqLiteDatabase;
    }

    public synchronized void closeDatabase() {
        // Closing database
        sqLiteDatabase.close();
    }

    public ArrayList<Group> getAllGroups() {
        StudentGroupTable groupTable = new StudentGroupTable();
        return groupTable.getAllGroups();
    }

    public Group findGroup(long groupId) {
        StudentGroupTable groupTable = new StudentGroupTable();
        return groupTable.findGroupById(groupId);
    }
    //Find Student, it will Student Table
    //Insert Student
    //get all courses list

//    public synchronized long insertConsolidatedSensorStats(SensorDeviceStats sensorStats) {
//        ConsolidatedStatsTable consolidatedSensorStatsTable = new ConsolidatedStatsTable();
//        return consolidatedSensorStatsTable.insertConsolidatedSensorStats(sensorStats);
//    }
//
//
//    public synchronized void deleteAllSensorStats() {
//        SensorStatsTable sensorStatsTable = new SensorStatsTable();
//        sensorStatsTable.deleteAllData();
//    }
//
//    public synchronized void updateAllSensorStatsByStatus(ArrayList<SensorDeviceStats> list) {
//        SensorStatsTable sensorStatsTable = new SensorStatsTable();
//        sensorStatsTable.updateAllDataByStatus(list);
//    }
//

}

