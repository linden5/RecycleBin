package com.example.lyx.myapplication.ch06;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.lyx.myapplication.R;

/**
 * Created by lyx on 2016/7/17.
 */
public class EmailActivity extends Activity {
    private EditText to, subject, content;
    private Button send;
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String strTo = to.getText().toString();
            String strSubject = subject.getText().toString();
            String strContent = content.getText().toString();

            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("plain/text");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{strTo});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, strSubject);
            emailIntent.putExtra(Intent.EXTRA_TEXT, strContent);

            startActivity(Intent.createChooser(emailIntent, "Emailing..."));
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        to = (EditText)findViewById(R.id.toEditText01);
        subject = (EditText)findViewById(R.id.subjectEditText01);
        content = (EditText)findViewById(R.id.contentEditText01);
        send = (Button)findViewById(R.id.sendButton01);

        send.setOnClickListener(listener);
    }
}
