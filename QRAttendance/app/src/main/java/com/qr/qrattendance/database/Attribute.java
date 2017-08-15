package com.qr.qrattendance.database;

/**
 * Created by waqas on 8/15/17.
 */

public class Attribute {
    public static enum Type {TEXT, INTEGER , Boolean , Decimal , Float , PRIMARY_KEY};
    private String columnName;
    private Type columnType;

    public Attribute(String columnName, Type columnType) {
        this.columnName = columnName;
        this.columnType = columnType;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Type getColumnType() {
        return columnType;
    }

    public void setColumnType(Type columnType) {
        this.columnType = columnType;
    }
}
