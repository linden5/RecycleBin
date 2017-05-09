package com.example.lyx.myapplication.ch09;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.lyx.myapplication.R;
import com.example.lyx.myapplication.ch06.ActionActivity1;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by lyx on 2016/7/18.
 */
public class FileActivity extends Activity {
    private static final String FILE_NAME = "temp.txt";
    private Button b1, b2;
    private EditText et1, et2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        b1 = (Button)findViewById(R.id.sendButton01);
        b2 = (Button)findViewById(R.id.sendButton02);
        et1 = (EditText)findViewById(R.id.subjectEditText01);
        et2 = (EditText)findViewById(R.id.contentEditText01);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                write(et1.getText().toString());
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et2.setText(read());
            }
        });
    }

    private String read() {
        try {
            FileInputStream fis = openFileInput(FILE_NAME);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            return new String(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void write(String content) {
        try {
            FileOutputStream fos = openFileOutput(FILE_NAME, MODE_APPEND);
            fos.write(content.getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
