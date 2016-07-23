package com.example.lyx.myapplication.ch11;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Size;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.lyx.myapplication.R;
import com.example.lyx.myapplication.ch10.Employees;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by lyx on 2016/7/21.
 */
public class CameraActivity extends Activity {
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private Camera camera;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);
        surfaceView = (SurfaceView)findViewById(R.id.preview);
        surfaceHolder = surfaceView.getHolder();

        surfaceHolder.addCallback(surfaceCallback);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            camera = Camera.open();
            try {
                camera.setPreviewDisplay(surfaceHolder);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
            Camera.Parameters parameters = camera.getParameters();
            List<Camera.Size> ps = parameters.getSupportedPreviewSizes();
            Camera.Size s = ps.get(4);

            parameters.setPreviewSize(s.width, s.height);
            parameters.setPictureFormat(PixelFormat.JPEG);
            camera.setParameters(parameters);
            camera.startPreview();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    };

    class SavePictureTask extends AsyncTask<byte[], String, String> {
        @Override
        protected String doInBackground(byte[]... params) {
            File picture = new File(Environment.getExternalStorageDirectory(), "picture.jpg");
            if(picture.exists()) picture.delete();
            try {
                FileOutputStream fos = new FileOutputStream(picture.getPath());
                fos.write(params[0]);
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] bytes, Camera camera) {
            new SavePictureTask().execute(bytes);
            camera.startPreview();
        }
    };

    private void takePic() {
//        camera.stopPreview();
        camera.takePicture(null, null, pictureCallback);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ( keyCode == KeyEvent.KEYCODE_MENU) {
            takePic();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
