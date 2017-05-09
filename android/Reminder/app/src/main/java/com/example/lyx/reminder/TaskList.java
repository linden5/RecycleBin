package com.example.lyx.reminder;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by lyx on 2016/7/26.
 */
public final class TaskList {
    public static final String AUTHORITY = "com.example.lyx.provider.TaskList";

    private TaskList() {}

    public static final class Tasks implements BaseColumns {
        private Tasks() {}

        public static final Uri CONTENT_URI = Uri.parse("content://" +  AUTHORITY + "/taskLists");
        public static final String CONTENT_TYPE = "vnd.android.curser.dir/vnd.example.lyx.tasklist";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.example.lyx.tasklist";

        public static final String DEFAULT_SORT_ORDER = "created DESC";
        public static final String CONTENT = "content";
        public static final String CREATED = "created";
        public static final String DATE1 = "date1";
        public static final String TIME1 = "time1";
        public static final String ON_OFF = "on_off";
        public static final String ALARM = "alarm";
    }
}
