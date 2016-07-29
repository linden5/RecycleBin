package com.example.lyx.reminder;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by lyx on 2016/7/27.
 */
public class AlarmActivity extends Activity {
    public static final int ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm);
        Button btn = (Button)findViewById(R.id.cancelButton01);
        TextView tv = (TextView)findViewById(R.id.msgTextView01);

        String service = Context.NOTIFICATION_SERVICE;
        final NotificationManager nm = (NotificationManager)getSystemService(service);
        Notification n = new Notification();
        String msg = getIntent().getStringExtra("msg");
        n.tickerText = msg;
        tv.setText(msg);

        n.sound = Uri.parse("file:///storage/extSdCard/音乐/conquistador-maintheme.mp3");
        nm.notify(ID,n);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nm.cancel(ID);
                finish();
            }
        });
    }
}
