package com.example.lyx.reminder;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by lyx on 2016/7/26.
 */
public class TaskDetailActivity extends ListActivity {
    private ListView listView = null;
    private int mYear;
    private int mMonth;
    private int mDay;

    private int mHour;
    private int mMinute;

    private TextView dateName, dateDesc;
    private TextView timeName, timeDesc;
    private TextView contentName, contentDesc;

    private int on_off = 0;
    private int alarm = 0;

    static final int DATE_DIALOG_ID = 0;
    static final int TIME_DIALOG_ID = 1;

    private String content, date1, time1;
    private int id1;
    private CheckedTextView ctv1, ctv2;
    private LayoutInflater li;

    private void init(Intent intent) {
        Bundle b = intent.getBundleExtra("b");
        if (b != null) {
            id1 = b.getInt("id");
            content = b.getString("content");
            date1 = b.getString("date1");
            time1 = b.getString("time1");
            on_off = b.getInt("on_off");
            alarm = b.getInt("alarm");
        }

        if (date1 != null && date1.length() > 0) {
            String[] strs = date1.split("/");
            mYear = Integer.parseInt(strs[0]);
            mMonth = Integer.parseInt(strs[1]);
            mDay = Integer.parseInt(strs[2]);
        }

        if (time1 != null && time1.length() > 0) {
            String[] strs = time1.split(":");
            mHour = Integer.parseInt(strs[0]);
            mMinute = Integer.parseInt(strs[1]);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listView = getListView();
        li = getLayoutInflater();
        listView.setAdapter(new ViewAdapter());
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> av, View v, int position, long id) {
                switch (position) {
                    case 0:
                        ctv1 = (CheckedTextView) v;
                        if (ctv1.isChecked()) {
                            on_off = 0;
                        } else {
                            on_off = 1;
                        }
                        break;
                    case 1:
                        showDialog(DATE_DIALOG_ID);
                        break;
                    case 2:
                        showDialog(TIME_DIALOG_ID);
                        break;
                    case 3:
                        showDialog1("Please enter:");
                        break;
                    case 4:
                        ctv2 = (CheckedTextView) v;
                        if (ctv2.isChecked()) {
                            alarm = 0;
                            setAlarm(false);
                        } else {
                            alarm = 1;
                            setAlarm(true);
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        init(getIntent());
    }

    class ViewAdapter extends BaseAdapter {
        String[] strs = {"On/off", "Date", "Time", "Content", "Alarm on"};

        @Override
        public int getCount() {
            return strs.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = li.inflate(R.layout.item_row, null);
            switch (position) {
                case 0:
                    ctv1 = (CheckedTextView) li.inflate(android.R.layout.simple_list_item_multiple_choice, null);
                    ctv1.setText(strs[position]);
                    if (on_off == 0) {
                        ctv1.setChecked(false);
                    } else {
                        ctv1.setChecked(true);
                    }
                    return ctv1;
                case 1:
                    dateName = (TextView)v.findViewById(R.id.name);
                    dateDesc = (TextView)v.findViewById(R.id.desc);
                    dateName.setText(strs[position]);
                    dateDesc.setText(mYear + "/" + mMonth + "/" + mDay);
                    return v;
                case 2:
                    timeName = (TextView)v.findViewById(R.id.name);
                    timeDesc = (TextView)v.findViewById(R.id.desc);
                    timeName.setText(strs[position]);
                    timeDesc.setText(mHour + "/" + mMinute);
                    return v;
                case 3:
                    contentName = (TextView)v.findViewById(R.id.name);
                    contentDesc = (TextView)v.findViewById(R.id.desc);
                    contentName.setText(strs[position]);
                    contentName.setText(content);
                    return v;
                case 4:
                    ctv2 = (CheckedTextView) li.inflate(android.R.layout.simple_list_item_multiple_choice, null);
                    ctv2.setText(strs[position]);
                    if (alarm == 0) {
                        ctv2.setChecked(false);
                    } else {
                        ctv1.setChecked(true);
                    }
                    return ctv2;
                default:
                    break;
            }
            return null;
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
            case TIME_DIALOG_ID:
                return new TimePickerDialog(this, mTimeSetListener, mHour, mMinute, false);
        }
        return null;
    }

    private void setAlarm(boolean flag) {
        final String BC_ACTION = "TaskReceiver";
        final AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);

        Intent intent = new Intent();
        intent.setAction(BC_ACTION);
        intent.putExtra("msg", content);
        final PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

        final long time1 = System.currentTimeMillis();
        Calendar c = Calendar.getInstance();
        c.set(mYear, mMonth, mDay, mHour, mMinute);
        long time2 = c.getTimeInMillis();

        if (flag && (time2 - time1) > 0 && on_off ==1) {
            am.set(AlarmManager.RTC_WAKEUP, time2, pi);
        } else {
            am.cancel(pi);
        }
    }

    private void showDialog1(String msg) {
        View v = li.inflate(R.layout.item_content, null);
        final EditText contentET = (EditText)v.findViewById(R.id.content);
        contentET.setText(content);
        new AlertDialog.Builder(this).setView(v).setMessage(msg).setCancelable(false).setPositiveButton("ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        content = contentET.getText().toString();
                        contentDesc.setText(content);
                    }
                }).show();
    }

    private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            mHour = i;
            mMinute = i1;
            timeDesc.setText(mHour + ":" + mMinute);
        }
    };

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            mYear = i;
            mMonth = i1;
            mDay = i2;
            dateDesc.setText(mYear + "/" + mMonth + "/" + mDay);
        }
    };

    protected void onPause() {
        super.onPause();
        saveOrUpdate();
    }

    private void saveOrUpdate() {
        ContentValues values = new ContentValues();
        values.clear();
        values.put(TaskList.Tasks.CONTENT, contentDesc.getText().toString());
        values.put(TaskList.Tasks.DATE1, dateDesc.getText().toString());
        values.put(TaskList.Tasks.TIME1, timeDesc.getText().toString());

        values.put(TaskList.Tasks.ON_OFF, ctv1.getText().toString());
        values.put(TaskList.Tasks.ALARM, ctv2.getText().toString());

        if (id1 != 0) {
            Uri uri = ContentUris.withAppendedId(TaskList.Tasks.CONTENT_URI, id1);
            getContentResolver().update(uri, values, null, null);
        } else {
            Uri uri = TaskList.Tasks.CONTENT_URI;
            getContentResolver().insert(uri, values);
        }
    }
}
