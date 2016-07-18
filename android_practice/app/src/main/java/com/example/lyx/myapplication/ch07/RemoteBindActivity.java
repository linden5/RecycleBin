package com.example.lyx.myapplication.ch07;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.lyx.myapplication.R;

/**
 * Created by lyx on 2016/7/17.
 */
public class RemoteBindActivity extends Activity {
    private IPerson iPerson;
    private Button btn;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        synchronized public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            iPerson = IPerson.Stub.asInterface(iBinder);
            if (iPerson != null)
                try {
                    iPerson.setName("hh");
                    iPerson.setAge(30);
                    String msg = iPerson.display();
                    Toast.makeText(RemoteBindActivity.this, msg, Toast.LENGTH_LONG).show();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button)findViewById(R.id.Button01);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("MY_REMOTE_SERVICE");
                bindService(intent, conn, Service.BIND_AUTO_CREATE);
            }
        });
    }
}
