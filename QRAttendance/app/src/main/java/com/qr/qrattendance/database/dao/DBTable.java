package com.qr.qrattendance.database.dao;

import android.content.ContentValues;

import com.qr.qrattendance.database.Attribute;
import com.qr.qrattendance.database.sqlite.DatabaseManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by waqas on 8/15/17.
 */

public  abstract class DBTable {
    public String tableName;
    List<Attribute> tableAttributes;

    public DBTable() {
        tableName = "";
        tableAttributes = new ArrayList<Attribute>();
        setTableName();
        setTableAttributes();
    }

    public abstract void setTableName();

    public abstract void setTableAttributes();

    public String createTable() {
        String querry = "CREATE TABLE " + tableName + "(";
        for (Attribute attributes :
                tableAttributes) {
            if (attributes.getColumnType().equals(Attribute.Type.PRIMARY_KEY)) {
                querry = querry + attributes.getColumnName() + "   INTEGER PRIMARY KEY AUTOINCREMENT    ";
            } else {
                querry = querry + " , " + attributes.getColumnName() + " " + attributes.getColumnType();
            }
        }
        querry = querry + ")";
        return querry;
    }
    public long insertData(ContentValues values) {
        long id = DatabaseManager.getInstance().openDatabase().insert(tableName, null, values);
        DatabaseManager.getInstance().closeDatabase();
        return id;
    }

    public void deleteAllData() {
        DatabaseManager.getInstance().openDatabase().execSQL("delete from " + tableName);
        DatabaseManager.getInstance().closeDatabase();
    }

}
