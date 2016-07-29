package com.example.lyx.myapplication.ch13;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.lyx.myapplication.R;

import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.AccessControlContext;

/**
 * Created by lyx on 2016/7/23.
 */
public class TestSocketActivity extends Activity {
    private TextView myTextView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myTextView = (TextView)findViewById(R.id.TextView01);
        try {
            Socket socket = new Socket("192.168.3.19", 8888);
            InputStream in = socket.getInputStream();
            byte[] buffer = new byte[in.available()];
            in.read(buffer);
            String msg = new String(buffer);
            myTextView.setText(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
