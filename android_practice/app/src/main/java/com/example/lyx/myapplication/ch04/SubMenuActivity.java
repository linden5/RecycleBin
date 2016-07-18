package com.example.lyx.myapplication.ch04;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

import com.example.lyx.myapplication.R;

/**
 * Created by lyx on 2016/7/15.
 */
public class SubMenuActivity extends Activity {
    private static final int ITEM1 = Menu.FIRST;
    private static final int ITEM2 = Menu.FIRST + 1;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        SubMenu file = menu.addSubMenu("File");
        SubMenu edit = menu.addSubMenu("Edit");
        file.add(0, ITEM1, 0, "New");
        file.add(0, ITEM2, 0, "Open");
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case ITEM1:
                setTitle("New File!");
                break;
            case ITEM2:
                setTitle("Open File!");
                break;
        }
        return true;
    }
}
