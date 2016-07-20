package com.example.lyx.myapplication.ch10;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import com.example.lyx.myapplication.ch05.SecondStepActivity;

import java.util.HashMap;

/**
 * Created by lyx on 2016/7/19.
 */
public class EmployeeProvider extends ContentProvider {
    private DBHelper dbHelper;
    private static final UriMatcher sUriMatcher;
    private static final int EMPLOYEE = 1;
    private static final int EMPLOYEE_ID = 2;
    private static HashMap<String, String> empProjectionMap;

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(Employees.AUTHORITY, "employee", EMPLOYEE);
        sUriMatcher.addURI(Employees.AUTHORITY, "employee/#", EMPLOYEE_ID);

        empProjectionMap = new HashMap<String, String>();
        empProjectionMap.put(Employees.Employee._ID, Employees.Employee._ID);
        empProjectionMap.put(Employees.Employee.NAME, Employees.Employee.NAME);
        empProjectionMap.put(Employees.Employee.GENDER, Employees.Employee.GENDER);
        empProjectionMap.put(Employees.Employee.AGE, Employees.Employee.AGE);
    }

    public boolean onCreate() {
        dbHelper = new DBHelper(getContext());
        return true;
    }

    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long rowId = db.insert(DBHelper.EMPLOYEES_TABLE_NAME, Employees.Employee.NAME, values);
        if (rowId > 0) {
            Uri empUri = ContentUris.withAppendedId(Employees.Employee.CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(empUri, null);
            return empUri;
        }
        return null;
    }

    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count;
        switch (sUriMatcher.match(uri)) {
            case EMPLOYEE:
                count = db.delete(DBHelper.EMPLOYEES_TABLE_NAME, selection, selectionArgs);
                break;
            case EMPLOYEE_ID:
                String noteId = uri.getPathSegments().get(1);
                count = db.delete(DBHelper.EMPLOYEES_TABLE_NAME,
                        Employees.Employee._ID + "=" + noteId
                                + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ")" : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Wrong URI:" + uri );
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    public String getType(Uri uri) {
        return null;
    }

    public Cursor query(Uri uri,String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        switch (sUriMatcher.match(uri)) {
            case EMPLOYEE:
                qb.setTables(DBHelper.EMPLOYEES_TABLE_NAME);
                qb.setProjectionMap(empProjectionMap);
                break;
            case EMPLOYEE_ID:
                qb.setTables(DBHelper.EMPLOYEES_TABLE_NAME);
                qb.setProjectionMap(empProjectionMap);
                qb.appendWhere(EMPLOYEE_ID + "=" + uri.getPathSegments().get(1));
                break;
            default:
                throw new IllegalArgumentException("Wrong Uri!:" + uri);
        }

        String orderBy;
        if (TextUtils.isEmpty(sortOrder)) {
            orderBy = Employees.Employee.DEFAULT_SORT_ORDER;
        } else {
            orderBy = sortOrder;
        }

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count;
        switch (sUriMatcher.match(uri)) {
            case EMPLOYEE:
                count = db.update(DBHelper.EMPLOYEES_TABLE_NAME, values, selection, selectionArgs);
                break;
            case EMPLOYEE_ID:
                String noteId = uri.getPathSegments().get(1);
                count = db.update(DBHelper.EMPLOYEES_TABLE_NAME, values,
                        Employees.Employee._ID + "=" + noteId
                                + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ")" : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Wrong URI:" + uri );
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
