package com.example.lyx.myapplication.ch04;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Contacts;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;

/**
 * Created by lyx on 2016/7/17.
 */
public class ListView2Activity extends ListActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Cursor c = getContentResolver().query(Contacts.People.CONTENT_URI, null, null, null, null);
        startManagingCursor(c);

        ListAdapter adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1,
                c,
                new String[] {Contacts.People.NAME},
                new int[] {android.R.id.text1});
        setListAdapter(adapter);
    }
}
