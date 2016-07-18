package com.example.lyx.myapplication.ch04;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

/**
 * Created by lyx on 2016/7/17.
 */
public class ListView1Activity extends ListActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] strs = {"Java", "C", "C++", "VB"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strs);
        setListAdapter(adapter);
    }
}
