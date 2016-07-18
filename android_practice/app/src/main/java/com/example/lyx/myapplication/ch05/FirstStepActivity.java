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
public class FirstStepActivity extends Activity {
    private EditText username, password;
    private Button btn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ch05_one);
        btn = (Button)findViewById(R.id.Button01);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = (EditText)findViewById(R.id.username);
                password = (EditText)findViewById(R.id.password);

                String str_username = username.getText().toString();
                String str_password = password.getText().toString();

                Bundle b = new Bundle();
                b.putString("username", str_username);
                b.putString("password", str_password);

                Intent intent = new Intent(FirstStepActivity.this, SecondStepActivity.class);
                intent.putExtras(b);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bundle b = data.getExtras();
        String str_username = b.getString("username");
        String str_password = b.getString("password");

        username.setText(str_username);
        password.setText(str_password);
    }
}
