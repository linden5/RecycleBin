package com.example.lyx.myapplication.ch10;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lyx on 2016/7/19.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Employees.db";
    private static final int DATABASE_VERSION = 1;
    public static final String EMPLOYEES_TABLE_NAME = "employee";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + EMPLOYEES_TABLE_NAME + " ("
                + Employees.Employee._ID + " INTEGER PRIMARY KEY,"
                + Employees.Employee.NAME + " TEXT,"
                + Employees.Employee.GENDER + " TEXT,"
                + Employees.Employee.AGE + " INTEGER"
                + ");");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS employee");
        onCreate(db);
    }
}
