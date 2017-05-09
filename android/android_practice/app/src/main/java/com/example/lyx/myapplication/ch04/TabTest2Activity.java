package com.example.lyx.myapplication.ch04;

import android.app.TabActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyx on 2016/7/16.
 */
public class TabTest2Activity extends TabActivity implements TabHost.TabContentFactory {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TabHost th = getTabHost();
        th.addTab(th.newTabSpec("all").setIndicator("All").setContent(this));
        th.addTab(th.newTabSpec("ok").setIndicator("R").setContent(this));
        th.addTab(th.newTabSpec("cancel").setIndicator("UnR").setContent(this));
    }

    public View createTabContent(String tag) {
        ListView lv = new ListView(this);
        List<String> list = new ArrayList<String>();
        list.add(tag);
        if(tag.equals("all")) {
            list.add("tom");
            list.add("rose");
            list.add("jack");
        } else if (tag.equals("ok")) {
            list.add("tom");
            list.add("ben");
        } else {
            list.add("rose");
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_checked, list);
        lv.setAdapter(adapter);
        return lv;
    }
}
