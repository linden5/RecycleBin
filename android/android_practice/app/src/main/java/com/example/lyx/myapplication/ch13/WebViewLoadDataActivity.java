package com.example.lyx.myapplication.ch13;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.lyx.myapplication.R;
import com.example.lyx.myapplication.ch04.WebViewTestActivity;

/**
 * Created by lyx on 2016/7/23.
 */
public class WebViewLoadDataActivity extends Activity {
    private WebView webView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web);
        webView = (WebView)findViewById(R.id.webview);

        String html = "";
        html += "<html>";
        html += "<body>";
        html += "<a href=http://cn.bing.com>Bing</a>";
        html += "</body></html>";

        webView.loadData(html, "text/html", "utf-8");
    }

}
