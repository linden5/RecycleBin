package com.example.lyx.myapplication.ch13;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.lyx.myapplication.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by lyx on 2016/7/23.
 */
public class LoginActivity extends Activity {
    private Button cancelBtn, loginBtn;
    private EditText userEdt, pwdEdt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        cancelBtn = (Button)findViewById(R.id.sendButton01);
        loginBtn = (Button)findViewById(R.id.sendButton02);
        userEdt = (EditText)findViewById(R.id.toEditText01);
        pwdEdt = (EditText)findViewById(R.id.subjectEditText01);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = userEdt.getText().toString();
                String password = pwdEdt.getText().toString();
                login(username, password);
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void showDialog(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(msg).setCancelable(false).setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void login(String username, String password) {
        String urlStr = "https://client.bestpay.com.cn/MEPF_INF2/httppost/queryAuthentication.idcard?";
        String queryString = "PRODUCTNO=" + username +"&LOCATION=" + password;
        urlStr += queryString;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                System.out.println(conn.getResponseCode());
                System.out.println(conn.getResponseMessage());
//            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream in = conn.getInputStream();
                byte[] b = new byte[in.available()];
                in.read(b);
                String msg = new String(b);
                showDialog(msg);
                in.close();
//            }
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private void login2(String username, String password) {
//        String urlStr = "http://";
//        HttpPost request = new HttpPost(urlStr);
//
//        List<NameValuePair> params = new ArrayList<NameValuePair>();
//        params.add(new BasicNameValuePair("username", username));
//        params.add(new BasicNameValuePair("password", password));
//        try{
//            request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
//            HttpResponse response = new DefaultHttpClient().execute(request);
//            if (response.getStatusLine().getStatusCode() == 200) {
//                String msg = EntityUtils.toString(response.getEntity());
//                showDialog(msg);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
