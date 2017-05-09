package com.example.lyx.myapplication.ch04;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.lyx.myapplication.R;

import java.util.Objects;

/**
 * Created by lyx on 2016/7/17.
 */
public class GridViewTestActivity extends Activity {
    private GridView gv;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid);
        gv = (GridView)findViewById(R.id.GridView01);
        gv.setNumColumns(4);
        gv.setAdapter(new MyAdapter(this));
    }

    class MyAdapter extends BaseAdapter {
        private Integer[] imgs = {
                R.drawable.hehe,
                R.drawable.hehe,
                R.drawable.hehe,
                R.drawable.hehe,
                R.drawable.hehe,
                R.drawable.hehe,
                R.drawable.hehe,
                R.drawable.hehe,
                R.drawable.hehe,
                R.drawable.hehe,
                R.drawable.hehe,
                R.drawable.hehe
        };
        Context context;
        MyAdapter(Context context) {
            this.context = context;
        }

        public int getCount() {
            return imgs.length;
        }

        public Object getItem(int item) {
            return item;
        }

        public long getItemId(int id) {
            return id;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(context);
                imageView.setLayoutParams(new GridView.LayoutParams(45, 45));
                imageView.setAdjustViewBounds(false);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8,8,8,8);
            } else {
                imageView = (ImageView) convertView;
            }
            imageView.setImageResource(imgs[position]);
            return imageView;
        }
    }
}
