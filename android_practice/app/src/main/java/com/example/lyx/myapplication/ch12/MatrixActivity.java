package com.example.lyx.myapplication.ch12;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.example.lyx.myapplication.R;

/**
 * Created by lyx on 2016/7/22.
 */
public class MatrixActivity extends Activity {
    private MyView myView;
    private AlertDialog.Builder b;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myView = new MyView(MatrixActivity.this);
        b = new AlertDialog.Builder(this);
        setContentView(myView);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        b.setMessage("fuck");
        b.setPositiveButton("ok", null);
        b.create().show();

        return myView.onKeyDown(keyCode, event);
    }

    class MyView extends View {
        private Bitmap bm;
        private Matrix matrix = new Matrix();
        private float angle = 0.0f;
        private int w, h;
        private float scale = 1.0f;
        private boolean isScale = false;
        private int count = 0;

        public MyView(Context context) {
            super(context);
            bm = BitmapFactory.decodeResource(this.getResources(),
                    R.drawable.hehe);
            w = bm.getWidth();
            h = bm.getHeight();
            this.setFocusable(true);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            matrix.reset();
            if (!isScale) {
                matrix.setRotate(angle);
            } else {
                matrix.setScale(scale, scale);
            }
            Bitmap bm2 = Bitmap.createBitmap(bm, 0 ,0,w, h, matrix, true);
            canvas.drawBitmap(bm2, matrix, null);
        }

        @Override
        public boolean onKeyDown(int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_MENU) {
                if (count % 4 == 0) {
                    isScale = false;
                    angle++;
                }

                if (count % 4 == 1) {
                    isScale = false;
                    angle--;
                }

                if (count % 4 == 2) {
                    isScale = true;
                    if (scale < 2.0) {
                        scale += 0.1;
                    }
                }

                if (count % 4 == 3) {
                    isScale = true;
                    if (scale > 0.5)
                        scale -= 0.1;
                }
                count++;
                postInvalidate();
                return true;
            }

            return super.onKeyDown(keyCode, event);
        }
    }
}
