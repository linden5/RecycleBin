package com.example.lyx.myapplication.ch04;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lyx.myapplication.R;

/**
 * Created by lyx on 2016/7/16.
 */
public class ProgressBarTestActivity extends Activity {
    private Button btn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button)findViewById(R.id.Button01);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(0);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        ProgressDialog dialog = new ProgressDialog(this);

        dialog.setTitle("Test");
        dialog.setIndeterminate(true);
        dialog.setMessage("Loading");
        dialog.setCancelable(true);

        return dialog;
    }
}
