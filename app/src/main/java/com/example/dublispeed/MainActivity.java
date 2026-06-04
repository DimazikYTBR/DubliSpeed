package com.example.dublispeed;

import android.content.Context;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myWebView = findViewById(R.id.webView);

        myWebView.getSettings().setJavaScriptEnabled(true);

        myWebView.addJavascriptInterface(new WebAppInterface(this), "AndroidBridge");
        
        myWebView.loadUrl("file:///android_asset/index.html");
    }

    public class WebAppInterface {
        Context mContext;
        WebAppInterface(Context c) { mContext = c; }

@JavascriptInterface
public void runSpeedTest() {
    new Thread(() -> {
        try {
            long startTime = System.currentTimeMillis();
            java.net.URL url = new java.net.URL("https://speed.cloudflare.com/__down?bytes=5000000");
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();

            java.io.InputStream is = conn.getInputStream();
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
            }
            is.close();
            
            long endTime = System.currentTimeMillis();

            double timeSeconds = (endTime - startTime) / 1000.0;

            final int mbps = (timeSeconds > 0) ? (int) ((5 * 8) / timeSeconds) : 0;

            myWebView.post(() -> {
                myWebView.evaluateJavascript("updateSpeed(" + mbps + ")", null);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }).start();
}

@JavascriptInterface
public void fixInternet() {
    myWebView.post(() -> {
        mContext.startActivity(new android.content.Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
    });
}
    }
}