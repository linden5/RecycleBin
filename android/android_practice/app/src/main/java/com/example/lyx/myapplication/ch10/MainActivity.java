package com.example.lyx.myapplication.ch10;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.example.lyx.myapplication.R;

/**
 * Created by lyx on 2016/7/19.
 */
public class MainActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        insert();
        query();
        update();
        query();
        del();
        query();
    }

    private void del() {
        Uri uri = ContentUris.withAppendedId(Employees.Employee.CONTENT_URI, 1);
        getContentResolver().delete(uri, null, null);
    }

    private void update() {
        Uri uri = ContentUris.withAppendedId(Employees.Employee.CONTENT_URI, 1);
        ContentValues values = new ContentValues();

        values.put(Employees.Employee.NAME, "fuck");
        values.put(Employees.Employee.GENDER, "fuck");
        values.put(Employees.Employee.AGE, 27);
        getContentResolver().update(uri, values, null, null);
    }

    private void query() {
        String[] PROJECTION = new String[] {
                Employees.Employee._ID,
                Employees.Employee.NAME,
                Employees.Employee.GENDER,
                Employees.Employee.AGE
        };

        Cursor c = managedQuery(Employees.Employee.CONTENT_URI, PROJECTION, null, null, Employees.Employee.DEFAULT_SORT_ORDER);
        if (c.moveToFirst()) {
            for (int i = 0; i < c.getCount(); i++) {
                c.moveToPosition(i);
                String name = c.getString(1);
                String gender = c.getString(2);
                int age = c.getInt(3);
                Log.i("emp", name + ":" + gender + ":" + age);
            }
        }
    }

    private void insert() {
        Uri uri = Employees.Employee.CONTENT_URI;
        ContentValues values = new ContentValues();
        values.put(Employees.Employee.NAME, "hhhh");
        values.put(Employees.Employee.GENDER, "hhhh");
        values.put(Employees.Employee.AGE, 11);
        getContentResolver().insert(uri, values);
    }
}
