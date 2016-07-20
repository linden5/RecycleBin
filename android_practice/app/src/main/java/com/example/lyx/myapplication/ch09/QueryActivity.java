package com.example.lyx.myapplication.ch09;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.lyx.myapplication.R;

/**
 * Created by lyx on 2016/7/19.
 */
public class QueryActivity extends ListActivity {
    String[] from = {"_id", "name", "url", "desc"};
    int[] to = {R.id.text0, R.id.text1, R.id.text2, R.id.text3};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Browse Collection");

        final DBHelper helper = new DBHelper(this);
        Cursor c = helper.query();

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.row, c, from, to);

        ListView listView = getListView();
        listView.setAdapter(adapter);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final long temp = l;
                builder.setMessage("Sure to delete?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        helper.del((int)temp);
                        Cursor c = helper.query();

                        SimpleCursorAdapter adapter1 = new SimpleCursorAdapter(getApplicationContext(),R.layout.row, c, from, to);
                        ListView listView1 = getListView();
                        listView1.setAdapter(adapter1);
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog ad = builder.create();
                ad.show();
            }
        });
        helper.close();
    }
}
