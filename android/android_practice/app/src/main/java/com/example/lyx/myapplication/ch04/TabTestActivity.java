package com.example.lyx.myapplication.ch04;

import android.app.TabActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TabHost;

import com.example.lyx.myapplication.R;

/**
 * Created by lyx on 2016/7/16.
 */
public class TabTestActivity extends TabActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TabHost th = getTabHost();

        LayoutInflater.from(this).inflate(R.layout.tab, th.getTabContentView(), true);
        th.addTab(th.newTabSpec("all").setIndicator("All").setContent(R.id.TextView01));
        th.addTab(th.newTabSpec("ok").setIndicator("Received").setContent(R.id.TextView02));
        th.addTab(th.newTabSpec("cancel").setIndicator("Unreceived").setContent(R.id.TextView03));
    }
}
