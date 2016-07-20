package com.example.lyx.myapplication.ch08;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lyx.myapplication.R;

/**
 * Created by lyx on 2016/7/18.
 */
public class AlarmActivity extends Activity {
    private Button sendBtn, cancelBtn;
    private static final String BC_ACTION = MainActivity.MY_ACTION;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendBtn = (Button)findViewById(R.id.Button01);
        cancelBtn = (Button)findViewById(R.id.Button02);

        final AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);

        Intent intent = new Intent();
        intent.setAction(BC_ACTION);
        intent.putExtra("msg", "meeting");
        final PendingIntent pi = PendingIntent.getBroadcast(AlarmActivity.this, 0, intent, 0);
        final long time = System.currentTimeMillis();
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                am.setRepeating(AlarmManager.RTC_WAKEUP, time, 8000, pi);
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                am.cancel(pi);
            }
        });
    }
}
