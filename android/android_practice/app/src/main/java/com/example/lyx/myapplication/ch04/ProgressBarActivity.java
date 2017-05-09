package com.example.lyx.myapplication.ch04;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.lyx.myapplication.R;

/**
 * Created by lyx on 2016/7/16.
 */
public class ProgressBarActivity extends Activity {
    private Button b1, b2;
    ProgressBar progressBar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_bar);

        b1 = (Button)findViewById(R.id.Button01);
        b2 = (Button)findViewById(R.id.Button02);

        progressBar = (ProgressBar)findViewById(R.id.ProgressBar01);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.incrementProgressBy(1);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.incrementProgressBy(-1);
            }
        });
    }

}
