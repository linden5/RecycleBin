package com.example.lyx.myapplication.ch04;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.lyx.myapplication.BuildConfig;
import com.example.lyx.myapplication.R;

import java.util.Calendar;

/**
 * Created by lyx on 2016/7/17.
 */
public class DatePickerActivity extends Activity {
    private Button b1, b2;
    private TextView tv1, tv2;
    private Calendar c;
    private int m_year, m_month, m_day;
    private int m_hour, m_minute;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = (Button)findViewById(R.id.Button01);
        b2 = (Button)findViewById(R.id.Button02);
        c = Calendar.getInstance();
        m_year = c.get(Calendar.YEAR);
        m_month = c.get(Calendar.MONTH);
        m_day = c.get(Calendar.DAY_OF_MONTH);
        m_hour = c.get(Calendar.HOUR);
        m_minute = c.get(Calendar.MINUTE);

        tv1 = (TextView)findViewById(R.id.TextView01);
        tv1.setText(m_year + ":" + (m_month + 1) + ":" + m_day);
        tv2 = (TextView)findViewById(R.id.TextView02);
        tv2.setText(m_hour + ":" + m_minute);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(0);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(1);
            }
        });
    }

    protected Dialog onCreateDialog(int id) {
        if (id == 0)
            return new DatePickerDialog(this, l1, m_year, m_month, m_day);
        else
            return new TimePickerDialog(this, l2, m_hour, m_minute, false);
    }

    private DatePickerDialog.OnDateSetListener l1 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            m_year = i;
            m_month = i1;
            m_day = i2;
            tv1.setText(m_year + ":" + (m_month + 1) + ":" + m_day);
        }
    };

    private TimePickerDialog.OnTimeSetListener l2 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            m_hour = i;
            m_minute = i1;
            tv2.setText(m_hour + ":" + m_minute);
        }
    };
}
