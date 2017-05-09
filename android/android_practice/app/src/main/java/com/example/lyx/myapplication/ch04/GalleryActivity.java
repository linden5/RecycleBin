package com.example.lyx.myapplication.ch04;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import com.example.lyx.myapplication.R;

import java.util.Objects;

/**
 * Created by lyx on 2016/7/17.
 */
public class GalleryActivity extends Activity implements AdapterView.OnItemSelectedListener,
        ViewSwitcher.ViewFactory {
    private ImageSwitcher mSwitcher;
    private Integer[] mThumbIds = {
            R.drawable.hehe,
            R.drawable.sprites,
            R.drawable.player
    };
    private Integer[] mImageIds = {
            R.drawable.hehe,
            R.drawable.sprites,
            R.drawable.player
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.gallery);
        mSwitcher = (ImageSwitcher)findViewById(R.id.switcher);
        mSwitcher.setFactory(this);
        mSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
        mSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));

        Gallery g = (Gallery)findViewById(R.id.gallery);
        g.setAdapter(new ImageAdapter(this));
        g.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapter, View v, int position, long id) {
        mSwitcher.setImageResource(mImageIds[position]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }

    @Override
    public View makeView() {
        ImageView i = new ImageView(this);
        i.setBackgroundColor(0xFF000000);
        i.setScaleType(ImageView.ScaleType.FIT_CENTER);
        i.setLayoutParams(new ImageSwitcher.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT
        ));
        return i;
    }

    public class ImageAdapter extends BaseAdapter {
        private Context mContext;
        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return mThumbIds.length;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent){
            ImageView i = new ImageView(mContext);
            i.setImageResource(mThumbIds[position]);
            i.setAdjustViewBounds(true);
            i.setLayoutParams(new Gallery.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT
            ));
            i.setBackgroundResource(android.R.drawable.picture_frame);
            return i;
        }
    }
}
