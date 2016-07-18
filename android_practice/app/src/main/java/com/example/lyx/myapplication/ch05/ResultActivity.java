package com.example.lyx.myapplication.ch05;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.widget.TextView;

import com.example.lyx.myapplication.R;

/**
 * Created by lyx on 2016/7/17.
 */
public class ResultActivity extends Activity {
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ch05_result);
        tv = (TextView)findViewById(R.id.TextView02);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        String name = b.getString("name");

        String[] projection = new String[] {Contacts.People._ID, Contacts.People.NAME, Contacts.People.NUMBER};
        Uri contacts = Contacts.People.CONTENT_URI;
        String[] args = {name};
        Cursor managedCursor = managedQuery(contacts, projection, "name=?", args, Contacts.People.NAME + " ASC");

        if(managedCursor.moveToFirst()) {
            String name1 = managedCursor.getString(1);
            String number = managedCursor.getString(2);
            tv.setText(name1 + ":" + number);
        }
    }
}
