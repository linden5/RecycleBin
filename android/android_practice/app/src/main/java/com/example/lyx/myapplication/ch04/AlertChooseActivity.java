package com.example.lyx.myapplication.ch04;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lyx.myapplication.R;

/**
 * Created by lyx on 2016/7/15.
 */
public class AlertChooseActivity extends Activity {
    private TextView myTV;
    private Button myBtn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myTV = (TextView)findViewById(R.id.TextView01);
        myBtn = (Button)findViewById(R.id.Button01);
        final String[] items = {"Orleans", "Spicy", "Coffee"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        myBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setTitle("Order Please")
                        .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                myTV.setText(items[i]);
                            }
                });
                AlertDialog ad = builder.create();
                ad.show();
            }
        });
    }
}
