package com.example.lyx.myapplication.ch04;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.ToggleButton;

import com.example.lyx.myapplication.R;
import com.example.lyx.myapplication.ch02.app.MainActivity;

/**
 * Created by lyx on 2016/7/16.
 */
public class WidgetActivity extends Activity {
    private Button register, cancel;
    private ToggleButton married;
    private RadioButton male, female;
    private EditText username, password;
    private Spinner position;
    private CheckBox reading, swimming;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);

        male = (RadioButton)findViewById(R.id.male);
        female = (RadioButton)findViewById(R.id.female);

        reading = (CheckBox)findViewById(R.id.reading);
        swimming = (CheckBox)findViewById(R.id.swimming);

        married = (ToggleButton)findViewById(R.id.married);
        position = (Spinner)findViewById(R.id.position);

        String[] str = {"CEO", "CFO", "PM"};

        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, str);
        position.setAdapter(aa);

        register = (Button)findViewById(R.id.register);
        cancel = (Button)findViewById(R.id.cancel);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString("username", "Username:" + username.getText().toString());
                b.putString("password", "Password:" + password.getText().toString());

                if (male.isChecked()) {
                    b.putString("gender", "Gender:Male");
                } else {
                    b.putString("gender", "Gender:Female");
                }

                String temp = "Hobby:";
                if (reading.isChecked()) {
                    temp += "reading";
                }
                if (swimming.isChecked()) {
                    temp += " swimming";
                }

                b.putString("hobby", temp);

                if (married.isChecked()) {
                    b.putString("married", "Married: Yes");
                } else {
                    b.putString("married", "Married: No");
                }

                b.putString("position", "Position:" + position.getSelectedItem().toString());
                Intent intent = new Intent(WidgetActivity.this, ResultActivity.class);
                intent.putExtra("data", b);
                startActivity(intent);
            }
        });
    }
}
