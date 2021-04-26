package com.istinye.week10sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {

    private DataBaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public static final String[] columnNames = new String[] { DataBaseHelper.STUDENT_ID, DataBaseHelper.STUDENT_NAME, DataBaseHelper.STUDENT_SURNAME };

    public DBManager(Context context) {
        this.context = context;
    }

    public DBManager open() {
        dbHelper = new DataBaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    public long insert(String name, String surname) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseHelper.STUDENT_NAME, name);
        contentValues.put(DataBaseHelper.STUDENT_SURNAME, surname);
        return database.insert(DataBaseHelper.TABLE_NAME, null, contentValues);
    }

    public Cursor fetch(){
        Cursor cursor = database.query(DataBaseHelper.TABLE_NAME, columnNames, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public void delete(long _id) {
        database.delete(DataBaseHelper.TABLE_NAME, DataBaseHelper.STUDENT_ID + "=" + _id, null);
    }

    public int update(long _id, String name, String surname) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseHelper.STUDENT_NAME, name);
        contentValues.put(DataBaseHelper.STUDENT_SURNAME, surname);
        return database.update(DataBaseHelper.TABLE_NAME, contentValues, DataBaseHelper.STUDENT_ID + "=" + _id, null);
    }
}
