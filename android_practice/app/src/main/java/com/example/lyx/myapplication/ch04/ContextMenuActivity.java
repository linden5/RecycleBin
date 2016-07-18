package com.example.lyx.myapplication.ch04;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.lyx.myapplication.R;

/**
 * Created by lyx on 2016/7/15.
 */
public class ContextMenuActivity extends Activity {
    private static final int ITEM1 = Menu.FIRST;
    private static final int ITEM2 = Menu.FIRST + 1;
    private static final int ITEM3 = Menu.FIRST + 2;
    private TextView myTV;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myTV = (TextView)findViewById(R.id.TextView01);
        registerForContextMenu(myTV);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(0, ITEM1, 0, "RedBG");
        menu.add(0, ITEM2, 0, "GreenBG");
        menu.add(0, ITEM3, 0, "WhiteBG");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case ITEM1:
                myTV.setBackgroundColor(Color.RED);
                break;
            case ITEM2:
                myTV.setBackgroundColor(Color.GREEN);
                break;
            case ITEM3:
                myTV.setBackgroundColor(Color.WHITE);
                break;
        }
        return true;
    }
}
