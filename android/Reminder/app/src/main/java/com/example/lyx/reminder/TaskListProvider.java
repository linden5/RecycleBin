package com.example.lyx.reminder;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by lyx on 2016/7/26.
 */
public class TaskListProvider extends ContentProvider {
    private static final String DATABASE_NAME = "task_list.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TASK_LIST_TABLE_NAME = "taskLists";
    private static HashMap<String, String> sTaskListProjectionMap;
    private static final int TASKS = 1;
    private static final int TASK_ID = 2;
    private static final UriMatcher sUriMatcher;
    private DatabaseHelper mOpenHelper;

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + TASK_LIST_TABLE_NAME + " ("
                            + TaskList.Tasks._ID + " TNTEGER PRIMARY KEY,"
                            + TaskList.Tasks.DATE1 + " TEXT,"
                            + TaskList.Tasks.TIME1 + " TEXT,"
                            + TaskList.Tasks.CONTENT + " TEXT,"
                            + TaskList.Tasks.ON_OFF + " INTEGER,"
                            + TaskList.Tasks.ALARM + " INTEGER,"
                            + TaskList.Tasks.CREATED + " TEXT"
                            + ");"
            );
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS taskLists");
            onCreate(db);
        }
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        switch (sUriMatcher.match(uri)) {
            case TASKS:
                qb.setTables(TASK_LIST_TABLE_NAME);
                qb.setProjectionMap(sTaskListProjectionMap);
                break;
            case TASK_ID:
                qb.setTables(TASK_LIST_TABLE_NAME);
                qb.setProjectionMap(sTaskListProjectionMap);
                qb.appendWhere(TaskList.Tasks._ID + "=" + uri.getPathSegments().get(1));
                break;
            default:
                throw new IllegalArgumentException("Uri error! " + uri);
        }
        String orderBy;

        if (TextUtils.isEmpty(sortOrder)) {
            orderBy = TaskList.Tasks.DEFAULT_SORT_ORDER;
        } else {
            orderBy = sortOrder;
        }

        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor c = qb.query(db, projection, selection, selectArgs, null, null, orderBy);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case TASKS:
                return TaskList.Tasks.CONTENT_TYPE;
            case TASK_ID:
                return TaskList.Tasks.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Wrong Uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues initialValues) {
        if (sUriMatcher.match(uri) != TASKS) {
            throw new IllegalArgumentException("Wrong URI:" + uri);
        }

        ContentValues values;
        if (initialValues != null) {
            values = new ContentValues(initialValues);
        } else {
            values = new ContentValues();
        }

        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        long rowId = db.insert(TASK_LIST_TABLE_NAME, TaskList.Tasks.CONTENT, values);
        if (rowId > 0) {
            Uri taskUri = ContentUris.withAppendedId(TaskList.Tasks.CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(taskUri, null);
            return taskUri;
        }
//        throw new SQLException("Insert failed " + uri);
        return null;
    }

    @Override
    public int delete(Uri uri, String where, String[] whereArgs) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count;
        switch (sUriMatcher.match(uri)) {
            case TASKS:
                count = db.delete(TASK_LIST_TABLE_NAME, where, whereArgs);
                break;
            case TASK_ID:
                String noteId = uri.getPathSegments().get(1);
                count = db.delete(TASK_LIST_TABLE_NAME, TaskList.Tasks._ID + "=" + noteId
                        + (!TextUtils.isEmpty(where) ? " AND ?" + where + ")" : ""), whereArgs
                );
                break;
            default:
                throw new IllegalArgumentException("Wrong Uri " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count;
        switch (sUriMatcher.match(uri)) {
            case TASKS:
                count = db.update(TASK_LIST_TABLE_NAME, values, where, whereArgs);
                break;
            case TASK_ID:
                String noteId = uri.getPathSegments().get(1);
                count = db.update(TASK_LIST_TABLE_NAME, values, TaskList.Tasks._ID + "=" + noteId
                    +(!TextUtils.isEmpty(where) ? " AND (" + where + ")" : ""), whereArgs
                );
                break;
            default:
                throw new IllegalArgumentException("Wrong uri " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(TaskList.AUTHORITY, "taskLists", TASKS);
        sUriMatcher.addURI(TaskList.AUTHORITY, "taskLists/#", TASK_ID);

        sTaskListProjectionMap = new HashMap<String, String>();
        sTaskListProjectionMap.put(TaskList.Tasks._ID, TaskList.Tasks._ID);
        sTaskListProjectionMap.put(TaskList.Tasks.CONTENT, TaskList.Tasks.CONTENT);
        sTaskListProjectionMap.put(TaskList.Tasks.CREATED, TaskList.Tasks.CREATED);

        sTaskListProjectionMap.put(TaskList.Tasks.ALARM, TaskList.Tasks.ALARM);
        sTaskListProjectionMap.put(TaskList.Tasks.DATE1, TaskList.Tasks.DATE1);
        sTaskListProjectionMap.put(TaskList.Tasks.TIME1, TaskList.Tasks.TIME1);
        sTaskListProjectionMap.put(TaskList.Tasks.ON_OFF, TaskList.Tasks.ON_OFF);
    }
}
