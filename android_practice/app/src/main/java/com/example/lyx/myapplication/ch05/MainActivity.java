package com.example.lyx.myapplication.ch05;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.lyx.myapplication.R;

/**
 * Created by lyx on 2016/7/17.
 */
public class MainActivity extends Activity {
    private Button btn;
    private EditText myEditText;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ch05_main);
        btn = (Button)findViewById(R.id.Button01);
        myEditText = (EditText)findViewById(R.id.EditText01);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = myEditText.getText().toString();
                Bundle data = new Bundle();
                data.putString("name", name);
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                intent.putExtras(data);
                startActivity(intent);
            }
        });
    }
}
