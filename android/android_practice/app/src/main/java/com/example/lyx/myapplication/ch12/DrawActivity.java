package com.example.lyx.myapplication.ch12;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.logging.Handler;

/**
 * Created by lyx on 2016/7/21.
 */
public class DrawActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyView v = new MyView(this, null);
        setContentView(v);
    }

    class MyView extends View implements Runnable {
        private int x = 20, y = 20;
        Paint p = new Paint();
        RefreshHandler mrh = new RefreshHandler();
        public MyView(Context context, AttributeSet attrs) {
            super(context, attrs);
            setFocusable(true);
            new Thread(this).start();
        }

        @Override
        public void run() {
//            Looper.prepare();
            while(!Thread.currentThread().isInterrupted()) {
                Message m = new Message();
                m.what = 0x101;
                mrh.sendMessage(m);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            p.setColor(Color.GREEN);
            canvas.drawCircle(x, y, 10, p);
        }

        class RefreshHandler extends android.os.Handler {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x101) {
                    MyView.this.update();
                    MyView.this.invalidate();
                }
                super.handleMessage(msg);
            }
        }

        private void update() {
            int h = getHeight();
            y += 5;
            if (y >= h) {
                y = 20;
            }
        }
    }
}
