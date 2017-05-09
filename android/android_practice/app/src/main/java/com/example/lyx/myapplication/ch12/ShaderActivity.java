package com.example.lyx.myapplication.ch12;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.example.lyx.myapplication.R;

/**
 * Created by lyx on 2016/7/22.
 */
public class ShaderActivity extends Activity {
    private MyView v;
    private AlertDialog.Builder b;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = new MyView(this);
        setContentView(v);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        b.setMessage("fuck");
        b.setPositiveButton("ok", null);
        b.create().show();

        return v.onKeyDown(keyCode, event);
    }

    class MyView extends View {
        private Bitmap bm;
        private Shader bitmapShader;
        private Shader linearGradient;
        private Shader radialGradient;
        private Shader sweepGradient;
        private Shader composeShader;
        private Paint paint;
        private int[] colors;
        private boolean isFirst = true;
        int count = 0;

        public MyView(Context context) {
            super(context);
            b = new AlertDialog.Builder(context);
            bm = BitmapFactory.decodeResource(getResources(), R.drawable.hehe);
            paint = new Paint();
            colors = new int[]{Color.RED, Color.GREEN, Color.BLUE};
            bitmapShader = new BitmapShader(bm, Shader.TileMode.REPEAT, Shader.TileMode.MIRROR);
            linearGradient = new LinearGradient(0, 0, 100, 100,
                    colors, null, Shader.TileMode.REPEAT);
            radialGradient = new RadialGradient(100, 100, 80,
                    colors, null, Shader.TileMode.REPEAT);
            sweepGradient = new SweepGradient(200, 200, colors, null);

            composeShader = new ComposeShader(linearGradient, radialGradient, PorterDuff.Mode.DARKEN);
            setFocusable(true);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            if (isFirst) {
                String content = "fuck!";
                paint.setColor(Color.BLUE);
                canvas.drawText(content, 0, content.length() - 1, 20, 20, paint);
            } else {
                canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
            }
        }

        @Override
        public boolean onKeyDown(int keyCode, KeyEvent event) {
            isFirst = false;

            if(keyCode == KeyEvent.KEYCODE_MENU) {
                if (count % 5 == 0) {
                    paint.setShader(bitmapShader);
                }
                if (count % 5 == 1) {
                    paint.setShader(linearGradient);
                }
                if (count % 5 == 2) {
                    paint.setShader(radialGradient);
                }
                if (count % 5 == 3) {
                    paint.setShader(sweepGradient);
                }
                if (count % 5 == 4) {
                    paint.setShader(composeShader);
                }
                count++;
                postInvalidate();
                return true;
            }

            return super.onKeyDown(keyCode, event);
        }
    }
}
