package com.qr.qrattendance.database.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.qr.qrattendance.database.Attribute;
import com.qr.qrattendance.database.sqlite.DatabaseManager;
import com.qr.qrattendance.model.Group;

import java.util.ArrayList;

/**
 * Created by waqas on 8/15/17.
 */

public class StudentGroupTable extends DBTable {
    public static final String TABLE_NAME = "st_group";
    public static final String COLUMN_GROUP_ID = "group_id";
    public static final String COLUMN_GROUP_NAME = "group_name";
    public static final String  COLUMN_CREATED_AT = "createdAt";
    public static final String  COLUMN_UPDATED_AT = "updatedAt";

    @Override
    public void setTableName() {
        tableName = TABLE_NAME;
    }

    @Override
    public void setTableAttributes() {
        tableAttributes.add(new Attribute(COLUMN_GROUP_ID,Attribute.Type.PRIMARY_KEY));
        tableAttributes.add(new Attribute(COLUMN_GROUP_NAME,Attribute.Type.TEXT));
        tableAttributes.add(new Attribute(COLUMN_CREATED_AT,Attribute.Type.TEXT));
        tableAttributes.add(new Attribute(COLUMN_UPDATED_AT,Attribute.Type.TEXT));
    }

    //return group by id
    public Group findGroupById(long id) {
        Group group = new Group();
        String[] columns = {COLUMN_GROUP_ID, COLUMN_GROUP_NAME};
        Cursor res = DatabaseManager.getInstance().openDatabase().query(tableName, columns, COLUMN_GROUP_ID + "=?", new String[]{"" + id}, null, null, null);

        while (res.moveToNext()) {
            group.setGroupId(res.getInt(res.getColumnIndex(COLUMN_GROUP_ID)));
            group.setGroupName(res.getString(res.getColumnIndex(COLUMN_GROUP_NAME)));
        }
        res.close();
        DatabaseManager.getInstance().closeDatabase();
        return group;
    }
    //insert new group
    public long insertGroup(Group group) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_GROUP_NAME, group.getGroupName());
        return insertData(values);
    }

    //return all groups list from group table
    public ArrayList<Group> getAllGroups() {
        ArrayList<Group> groupList = new ArrayList();
        String querry = "SELECT * FROM " + TABLE_NAME ;
        Cursor cursor = DatabaseManager.getInstance().openDatabase().rawQuery(querry, null);
        while (cursor.moveToNext()) {
           Group group = new Group();
            group.setGroupId(cursor.getLong(cursor.getColumnIndex(COLUMN_GROUP_ID)));
            group.setGroupName(cursor.getString(cursor.getColumnIndex(COLUMN_GROUP_NAME)));
            groupList.add(group);
        }
        DatabaseManager.getInstance().closeDatabase();
        return groupList;
    }

}
