package com.example.lyx.myapplication.ch08;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lyx.myapplication.R;

/**
 * Created by lyx on 2016/7/18.
 */
public class NotifActivity extends Activity {
    private Button sendBtn, cancelBtn;
    private Notification.Builder n;
    private NotificationManager nm;
    private static final int ID = 1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendBtn = (Button)findViewById(R.id.Button01);
        sendBtn.setText("Send");
        cancelBtn = (Button)findViewById(R.id.Button02);
        cancelBtn.setText("Cancel");

        String service = NOTIFICATION_SERVICE;
        nm = (NotificationManager)getSystemService(service);

        n = new Notification.Builder(NotifActivity.this);
        int icon =R.drawable.hehe;
        String tikerText = "Test Notification";
        long when = System.currentTimeMillis();
        n.setAutoCancel(false);
        n.setTicker(tikerText);
        n.setNumber(100);

        sendBtn.setOnClickListener(sendListener);
        cancelBtn.setOnClickListener(cancelListener);
    }

    private View.OnClickListener sendListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(NotifActivity.this, NotifActivity.class);
            PendingIntent pi = PendingIntent.getActivity(NotifActivity.this, 0, intent, 0);
            n.setContentTitle("Fuck");
            n.setContentText("Fuck API 19");
            n.setContentIntent(pi);
            n.setOngoing(true);
            n.setWhen(System.currentTimeMillis());
            n.setSmallIcon(R.drawable.hehe);
            nm.notify(ID, n.getNotification());
        }
    };

    private View.OnClickListener cancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            nm.cancel(ID);
        }
    };
}
