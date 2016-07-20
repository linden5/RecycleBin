package com.example.lyx.myapplication.ch09;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.lyx.myapplication.R;

/**
 * Created by lyx on 2016/7/18.
 */
public class PrefActivity extends Activity {
    private EditText myEditText;
    private Button btn;
    private static final String TEMP_SMS = "temp_sms";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        myEditText = (EditText)findViewById(R.id.contentEditText01);
        btn = (Button)findViewById(R.id.sendButton01);

        SharedPreferences pre = getSharedPreferences(TEMP_SMS, MODE_WORLD_READABLE);
        String content = pre.getString("sms_content", "");
        myEditText.setText(content);
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences.Editor editor = getSharedPreferences(TEMP_SMS, MODE_WORLD_WRITEABLE).edit();
        editor.putString("sms_content", myEditText.getText().toString());
        editor.commit();
    }
}
