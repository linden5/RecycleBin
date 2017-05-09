package com.example.lyx.myapplication.ch04;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lyx.myapplication.R;

/**
 * Created by lyx on 2016/7/16.
 */
public class EventActivity extends Activity {
    private EditText myEdit1, myEdit2;
    private CheckBox cb1;
    private Button b1,b2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_layout);
        myEdit1 = (EditText)findViewById(R.id.EditText01);
        myEdit2 = (EditText)findViewById(R.id.EditText02);
        cb1 = (CheckBox)findViewById(R.id.CheckBox01);
        b1 = (Button)findViewById(R.id.Button01);
        b2 = (Button)findViewById(R.id.Button02);

        myEdit1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                myEdit1.setText("");
                return false;
            }
        });

        myEdit2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                myEdit2.setText("");
                return false;
            }
        });

        myEdit1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Toast t = Toast.makeText(getApplicationContext(), myEdit1.getText(), Toast.LENGTH_LONG);
                t.show();
            }
        });

        myEdit2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Toast t = Toast.makeText(getApplicationContext(), myEdit2.getText(), Toast.LENGTH_LONG);
                t.show();
            }
        });

        cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Toast t = Toast.makeText(getApplicationContext(), cb1.isChecked() + "", Toast.LENGTH_LONG);
                t.show();
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast t = Toast.makeText(getApplicationContext(), b1.getText(), Toast.LENGTH_LONG);
                t.show();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast t = Toast.makeText(getApplicationContext(), b2.getText(), Toast.LENGTH_LONG);
                t.show();
            }
        });
    }
}
