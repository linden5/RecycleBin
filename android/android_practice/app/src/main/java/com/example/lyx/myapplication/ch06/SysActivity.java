package com.example.lyx.myapplication.ch06;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by lyx on 2016/7/17.
 */
public class SysActivity extends ListActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] menus = {"See phone info", "Edit phone info", "Show dial interface",
                "Directly dial", "Bing", "Geo"};
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menus));
        getListView().setTextFilterEnabled(true);
    }

    protected void onListItemClick(ListView l, View v, int posiiton, long id) {
        Intent intent = new Intent();
        Uri uri;
        String data;
        switch (posiiton) {
            case 0:
                data = "content://contacts/people/10";
                uri = Uri.parse(data);
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(uri);
                startActivity(intent);
                break;
            case 1:
                data = "content://contacts/people/10";
                uri = Uri.parse(data);
                intent.setAction(Intent.ACTION_EDIT);
                intent.setData(uri);
                startActivity(intent);
                break;
            case 2:
                data = "tel:13995991895";
                uri = Uri.parse(data);
                intent.setAction(Intent.ACTION_DIAL);
                intent.setData(uri);
                startActivity(intent);
                break;
            case 3:
                data = "tel:13995991895";
                uri = Uri.parse(data);
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(uri);
                startActivity(intent);
                break;
            case 4:
                data = "http://cn.bing.com";
                uri = Uri.parse(data);
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(uri);
                startActivity(intent);
                break;
            case 5:
                data = "geo:39.92,116.46";
                uri = Uri.parse(data);
                intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
