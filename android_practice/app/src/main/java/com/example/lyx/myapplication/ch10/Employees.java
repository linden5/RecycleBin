package com.example.lyx.myapplication.ch10;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by lyx on 2016/7/19.
 */
public final class Employees {
    public static final String AUTHORITY = "com.example.lyx.myapplication.ch10.Employees";
    private Employees(){}

    public static final class Employee implements BaseColumns {
        private Employee(){}

        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/employee");
        public static final String CONTENT_TYPE ="vnd.android.cursor.dir/vnd.amaker.employees";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.amaker.employees";

        public static final String DEFAULT_SORT_ORDER = "name DESC";
        public static final String NAME = "name";
        public static final String GENDER = "gender";
        public static final String AGE = "age";
    }
}
