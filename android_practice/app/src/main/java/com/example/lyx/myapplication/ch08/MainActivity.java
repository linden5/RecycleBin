package com.example.lyx.myapplication.ch08;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lyx.myapplication.R;

/**
 * Created by lyx on 2016/7/18.
 */
public class MainActivity extends Activity {
    static final String MY_ACTION = "com.example.lyx.myapplication.ch08.action.MY_ACTION";
    private Button btn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button)findViewById(R.id.Button01);
        btn.setText("BroadCast");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(MY_ACTION);
                intent.putExtra("msg", "hehehehehehehehehheheheheheheh");
                sendBroadcast(intent);
            }
        });
    }
}
