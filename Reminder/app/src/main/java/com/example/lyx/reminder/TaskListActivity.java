package com.example.lyx.reminder;

import android.app.ListActivity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * Created by lyx on 2016/7/26.
 */
public class TaskListActivity extends ListActivity {
    private static final int NEW = 1;
    private static final int DEL = 2;

    private static final String[] PROJECTION = new String[] {
            TaskList.Tasks._ID,
            TaskList.Tasks.CONTENT,
            TaskList.Tasks.CREATED,
            TaskList.Tasks.ALARM,
            TaskList.Tasks.DATE1,
            TaskList.Tasks.TIME1,
            TaskList.Tasks.ON_OFF
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Intent intent = getIntent();

        if (intent.getData() == null) {
            intent.setData(TaskList.Tasks.CONTENT_URI);
        }

        ListView listView = getListView();

        Cursor cursor = managedQuery(getIntent().getData(), PROJECTION, null, null, TaskList.Tasks.DEFAULT_SORT_ORDER);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor,
                new String[]{TaskList.Tasks._ID, TaskList.Tasks.CONTENT},
                new int[]{android.R.id.text1, android.R.id.text2});
        setListAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> av, View v, int position, long id) {
                Uri uri = ContentUris.withAppendedId(TaskList.Tasks.CONTENT_URI, id);
                Cursor cursor = managedQuery(uri, PROJECTION, null, null, TaskList.Tasks.DEFAULT_SORT_ORDER);

                if (cursor.moveToNext()) {
                    int id1 = cursor.getInt(0);
                    String content = cursor.getString(1);
                    String created = cursor.getString(2);
                    int alarm = cursor.getInt(3);
                    String date1 = cursor.getString(4);
                    String time1 = cursor.getString(5);
                    int on_off = cursor.getInt(6);
                    Bundle b = new Bundle();
                    b.putInt("id", id1);
                    b.putString("content", content);
                    b.putString("created", created);

                    b.putInt("alarm", alarm);
                    b.putString("date1", date1);
                    b.putString("time1", time1);

                    b.putInt("on_off", on_off);

                    intent.putExtra("b", b);
                    intent.setClass(TaskListActivity.this, TaskDetailActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, NEW, 0, "New");
        menu.add(0, DEL, 0, "Delete");
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch ( item.getItemId() ) {
            case NEW:
                Intent intent = new Intent();
                intent.setClass(this, TaskDetailActivity.class);
                startActivity(intent);
                return true;
            case DEL:
                return true;
        }
        return false;
    }
}
